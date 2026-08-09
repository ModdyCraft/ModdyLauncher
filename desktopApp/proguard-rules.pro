# Adoptium
-keep class com.moddy.moddylauncher.domain.adoptium.** { *; }

# Manifest
-keep class com.moddy.moddylauncher.domain.manifest.** { *; }

# Version
-keep class com.moddy.moddylauncher.domain.version.** { *; }

# Keep annotation definitions
-keep class org.koin.core.annotation.** { *; }

# Keep classes annotated with Koin annotations
-keep @org.koin.core.annotation.* class * { *; }

-keep class org.sqlite.** { *; }
-keep class app.cash.sqldelight.driver.jdbc.** { *; }

-keep class io.ktor.serialization.kotlinx.** { *; }
-keep class io.ktor.serialization.kotlinx.json.** { *; }

# Evitar que ProGuard elimine archivos en META-INF/services
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod
-keepdirectories META-INF/services