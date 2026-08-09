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