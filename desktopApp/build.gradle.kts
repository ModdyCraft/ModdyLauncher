import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

dependencies {
    implementation(project(":shared"))

    implementation(compose.desktop.currentOs)
    implementation(libs.kotlinx.coroutinesSwing)

    implementation(libs.compose.uiToolingPreview)
}

compose.desktop {
    application {
        mainClass = "com.moddy.moddylauncher.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "ModdyLauncher"
            packageVersion = "1.0.1"

            modules("java.sql")

            windows {
                // Activa la creación de accesos directos
                shortcut = true             // Crea un acceso directo en el Escritorio
                menu = true                 // Agrega la app al Menú Inicio de Windows
                // menuGroup = "ModdyLauncher"  // (Opcional) Nombre de la carpeta en el Menú Inicio

                // Opcional: Icono de la aplicación (.ico)
                iconFile.set(project.file("src/main/resources/icon.ico"))

                // Configuración opcional para instalación por usuario o sistema
                // dirChooser = true        // Permite al usuario elegir la carpeta de instalación
                // perUserInstall = true    // Instala solo para el usuario actual (sin pedir admin)
            }
        }

        buildTypes {
            release {
                proguard {
                    configurationFiles.from(
                        project.file("proguard-rules.pro")
                    )
                }
            }
        }
    }
}