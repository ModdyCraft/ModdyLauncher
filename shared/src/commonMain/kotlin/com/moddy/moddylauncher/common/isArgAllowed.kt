package com.moddy.moddylauncher.common

import com.moddy.moddylauncher.LauncherPaths
import com.moddy.moddylauncher.appName
import com.moddy.moddylauncher.appVersion
import com.moddy.moddylauncher.database.user.UserData
import com.moddy.moddylauncher.domain.version.DefaultUserJvm
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

fun isArgAllowed(arg: DefaultUserJvm): Boolean {
    if (arg.rules == null) return true

    val currentOs = when {
        LauncherPaths.os.contains("win", ignoreCase = true) -> "windows"
        LauncherPaths.os.contains("mac", ignoreCase = true) -> "osx"
        else -> "linux"
    }

    var allowed = false

    for ((action, os) in arg.rules) {
        if (os?.name != currentOs) continue

        // Si es Windows, comprobar el rango de versión
        if (currentOs == "windows" && os.versionRange != null) {
            val currentVersion = LauncherPaths.osVersion

            val minVersion = os.versionRange.min
            val maxVersion = os.versionRange.max

            if (minVersion != null && compareVersions(currentVersion, minVersion) < 0) {
                continue
            }

            if (maxVersion != null && compareVersions(currentVersion, maxVersion) > 0) {
                continue
            }
        }

        allowed = action == "allow"
    }

    return allowed
}

fun isArgAllowed(element: JsonObject): Boolean {
    val rules = element["rules"]?.jsonArray ?: return true

    var allowed = false

    for (rule in rules) {
        val ruleObject = rule.jsonObject
        val action = ruleObject["action"]?.jsonPrimitive?.content
            ?: continue

        val os = ruleObject["os"]?.jsonObject

        if (os == null) {
            allowed = action == "allow"
            continue
        }

        val currentOs = when {
            LauncherPaths.os.contains("win", ignoreCase = true) -> "windows"
            LauncherPaths.os.contains("mac", ignoreCase = true) -> "osx"
            else -> "linux"
        }

        val ruleOs = os["name"]?.jsonPrimitive?.content

        if (ruleOs == currentOs) {
            allowed = action == "allow"
        }
    }

    return allowed
}

fun compareVersions(
    current: String,
    target: String
): Int {
    val currentParts = current.split(".").map { it.toIntOrNull() ?: 0 }
    val targetParts = target.split(".").map { it.toIntOrNull() ?: 0 }

    val maxSize = maxOf(currentParts.size, targetParts.size)

    for (i in 0 until maxSize) {
        val currentPart = currentParts.getOrElse(i) { 0 }
        val targetPart = targetParts.getOrElse(i) { 0 }

        if (currentPart < targetPart) return -1
        if (currentPart > targetPart) return 1
    }

    return 0
}

fun resolveArgument(
    argument: String,
    versionId: String,
    assetIndex: String,
    versionType: String,
    userData: UserData
): String {

    val variables = mapOf(
        "\${auth_player_name}" to userData.username,
        "\${version_name}" to versionId,
        "\${game_directory}" to LauncherPaths.newProfile(versionId).root.absolutePath,
        "\${assets_root}" to LauncherPaths.assets.absolutePath.toString(),
        "\${assets_index_name}" to assetIndex,
        "\${auth_uuid}" to userData.uuid,
        "\${auth_access_token}" to "0",
        "\${clientid}" to versionId,
        "\${auth_xuid}" to "0",
        "\${version_type}" to versionType,
        "\${resolution_width}" to "DEFAULT",
        "\${resolution_height}" to "DEFAULT",
    )

    return variables[argument] ?: argument
}

fun resolveJVMArgument(
    argument: String,
): String {

    val variables = mapOf(
        "\${natives_directory}" to LauncherPaths.nativeDirectory.absolutePath,
        "\${launcher_name}" to appName,
        "\${launcher_version}" to appVersion
    )

    var result = argument

    for ((key, value) in variables) {
        if (result.contains(key)) {
            result = result.replace(key, value)
            break
        }
    }

    return result
}