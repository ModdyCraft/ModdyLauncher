This is a Kotlin Multiplatform project targeting Desktop (JVM).

# Moddy Launcher

**Moddy Launcher** es un launcher de Minecraft de código abierto y gratuito, creado con la intención de ofrecer una
alternativa libre y accesible para la comunidad.

El proyecto se encuentra actualmente **en desarrollo** y puede contener errores, limitaciones o funcionalidades
incompletas. Sin embargo, creemos que, con la colaboración de la comunidad, el proyecto puede seguir creciendo y
mejorando con el tiempo.

Nuestro objetivo es construir un launcher desarrollado de manera abierta, donde cualquier persona pueda contribuir,
aprender, proponer mejoras y ayudar a construir una herramienta útil para la comunidad.

## 🚧 Proyecto en desarrollo

Moddy Launcher todavía está en una etapa de desarrollo activo. Esto significa que pueden existir errores, problemas de
compatibilidad o características que aún no estén implementadas.

Si encuentras un problema, tienes una idea para mejorar el proyecto o quieres aportar nuevas funcionalidades, eres
bienvenido a participar.

Las contribuciones pueden incluir:

* 🐛 Reportar y solucionar errores.
* ✨ Proponer y desarrollar nuevas funcionalidades.
* 🧹 Mejorar y refactorizar el código existente.
* 📚 Crear y mejorar la documentación.
* 📝 Mejorar los comentarios y explicaciones del código.
* 🔍 Revisar el código y proponer mejoras.
* 💡 Compartir ideas y sugerencias para el futuro del proyecto.

**Si eres desarrollador, te invitamos a revisar el código, contribuir y ayudarnos a construir una versión cada vez mejor
de Moddy Launcher.**

## 💖 Un proyecto hecho por la comunidad

Moddy Launcher nace como un proyecto de código abierto y **no busca obtener beneficios monetarios**. El launcher es y
seguirá siendo un proyecto gratuito, creado con la intención de compartir conocimiento, fomentar la colaboración y
ofrecer una alternativa desarrollada de forma abierta.

No pretendemos que nadie obtenga beneficios económicos a costa del trabajo de otros. Nuestro objetivo es que el proyecto
pueda crecer gracias a las contribuciones voluntarias de la comunidad y al trabajo de quienes quieran formar parte de
él.

## ⚠️ Descargo de responsabilidad

Moddy Launcher **no es un producto oficial de Mojang Studios ni de Microsoft**, y no estamos afiliados, asociados ni
respaldados oficialmente por ninguna de estas compañías.

Este proyecto no pretende apropiarse del código, la marca o la propiedad intelectual de Minecraft. Minecraft y sus
elementos relacionados pertenecen a sus respectivos propietarios.

Moddy Launcher se desarrolla como un proyecto independiente de código abierto con fines educativos, experimentales y
comunitarios.

El proyecto tampoco tiene como objetivo fomentar la piratería ni promover el uso ilegal de copias de Minecraft.

## 🎮 Sobre Minecraft y las copias originales

Entendemos que no todas las personas tienen las mismas posibilidades económicas y que existen diferentes circunstancias
por las que alguien puede buscar una alternativa de launcher.

Sin embargo, **si tienes la posibilidad de comprar una copia original de Minecraft, te recomendamos hacerlo**.

Comprar el juego de forma oficial es la mejor manera de apoyar a sus desarrolladores y contribuir a que Minecraft
continúe recibiendo actualizaciones y soporte.

Moddy Launcher no pretende sustituir la compra del juego ni perjudicar a sus desarrolladores. Nuestra intención es
fomentar el acceso al software libre y al conocimiento, manteniendo siempre el respeto por los derechos de autor y la
propiedad intelectual.

## 🌱 Código abierto y colaboración

Creemos que el software puede mejorar cuando las personas comparten sus conocimientos y trabajan juntas.

Por eso, invitamos a cualquier persona interesada a participar en el desarrollo de Moddy Launcher. No importa si eres un
desarrollador experimentado o si estás comenzando a aprender: puedes contribuir de muchas maneras.

Puedes ayudar escribiendo código, corrigiendo errores, mejorando la documentación, traduciendo contenido, probando
nuevas versiones o simplemente compartiendo ideas.

Toda contribución, por pequeña que sea, puede ayudar al crecimiento del proyecto.

## 📌 Estado actual

> **Moddy Launcher está actualmente en desarrollo.**
>
> Algunas funciones pueden no estar terminadas y pueden existir errores o problemas inesperados. El proyecto seguirá
> evolucionando gracias al trabajo de sus colaboradores y de la comunidad.

Si quieres contribuir, mejorar el código o ayudar a documentar el proyecto, **¡eres bienvenido!**

---

**Moddy Launcher — Código abierto, gratuito y hecho por la comunidad.**

* [/shared](./shared/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
    - [commonMain](./shared/src/commonMain/kotlin) is for code that’s common for all targets.
    - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
      For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
      the [iosMain](./shared/src/iosMain/kotlin) folder would be the right place for such calls.
      Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./shared/src/jvmMain/kotlin)
      folder is the appropriate location.

### Running the apps

Use the run configurations provided by the run widget in your IDE's toolbar. You can also use these commands and
options:

- Desktop app:
    - Hot reload: `./gradlew :desktopApp:hotRun --auto`
    - Standard run: `./gradlew :desktopApp:run`

### Running tests

Use the run button in your IDE's editor gutter, or run tests using Gradle tasks:

- Desktop tests: `./gradlew :shared:jvmTest`

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…