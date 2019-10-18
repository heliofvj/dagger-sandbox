# Dagger 2 Sandbox

A IntelliJ Gradle project created after reading the (terrible) Dagger 2 documentation. These examples cover most of the features described in the Dagger 2 docs.

## Configuring Intellij IDEA extra steps

- Access: File | Settings | Build, Execution, Deployment | Compiler | Annotation Processors
- Enable annotation processing, then at "Store generate sources relative to:", mark "Module content root".
> Production sources directory should look like: "generated"
- Build the project
> A folder named "generated" should have been created at the src/main folder
- Now right-click the newly generated folder and select "Mark Directory As > Generated Sources Root"
