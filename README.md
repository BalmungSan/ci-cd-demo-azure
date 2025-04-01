# CI / CD demo for Azure

Demo showing how to use **Github Actions** to configure a `CI / CD` pipeline deploying a **Dockerized** **Scala** app to **Azure** using **Terraform**.

-----

1. Crear una cuenta de **Azure** con las credenciales de **EAFIT**.
2. Hacer un `fork` del repositorio.
3. Ver que al hacer el `fork` la rama principal trato de ejecutar un `workflow` de **Github Actions**, pero que este fallo porque falta configurar unos secretos.
4. En el portal de **Azure** buscar el servicio **Subscriptions**, seleccionas la `subscription` llamada `Azure for Students`, luego buscas **Resource Providers**, en la lista vas a buscar el `provider` llamado `Microsoft.App` y lo registras. Este es necesario para luego poder crear un **Container Application**.
5. En el portal de **Azure** buscar el servicio **Resource groups**, en este servicio vamos a crear `resource-group` para los meta-recursos _(recursos que son necesarios para que la infraestructura como código pueda crear los siguientes recursos)_. El nombre puede ser cualquiera, pero yo recomiendo `ci-cd-demo-azure-iac-rg` - NOTAIMPORTANTE: La región _(location)_ a usar debe ser `East US 2`.
6. En el portal de **Azure** buscar el servicio **Managed Identities**, en este servicio vamos a crear una `user-assigned-managed identity` que va a ser usada por **Terrform** para desplegar la aplicación. El nombre puede ser cualquiera, pero yo recomiendo `<EAFIT_ID>-iac-user` _(donde `EAFIT_ID` es tu nombre de usuario de la universidad; por ejemplo el mio es `lmejias3`)_, lo creas dentro del `resource-group` creado en el paso anterior, la región _(location)_ a usar debe ser `East US 2`, y le asignas la politica de `Owner` sobre la `subscription` llamada `Azure for Students`. - Luego, te vas para **Federated credentials**, creas un `federate-credential`, el `scenario` es `Github Actions deploying Azure resources`, la `entity` debe ser un `environment` llamado `dev`, la `organization` y `repository` son los de tu repositorio, y el nombre puede ser cauqluiera; yo use `gha`.
7. En el portal de **Azure** buscar el servicio **Storage Accounts**, en este servicio vamos a crear un `storage-account` y un `sotrage-container` que va a ser usado por **Terraform** para mantener el estado de la IaC. El nombre del `storage-acount` debe de ser tu `EAFIT_ID`, usar el `resource-group` creado anterior mente, la región _(location)_ a usar debe ser `East US 2`, el servcio primario debe de ser **Azure Blob Storage**, el performance `Standard`, y la replicación `LRS`. - Una vez creado el **Storage Account** te vas a **Containers** y creas uno con el nombre `ci-cd-demo-azure-iac`. - Por último, te vas a **Access Control (IAM)** y creas un `role-assignment`, el rol es `Storage Blob Data Owner` y se lo asignas al `User Assigned Managed Identity` que creamos anteriormente _(nota, si alguien quiere ejecutar los comandos en consola, debe de asigarse este role a su propio usuario también)_.
8. En **Github** vas a la cofniguración del repositorio y buscas **Actions Secrets and Variables**, vamos a configurar los secretos necesarios para poder desplegar la aplicación. Creamos la `variable` `EAFIT_ID`,  con tu usuario de **EAFIT**. Creamos el `secret` `DB_PASSWORD`, con la contraseña que queramos ponerle a la base de datos. Creamos el secreto `AZURE_CLIENT_ID`, el valor lo sacamos del usuario creado en el paso 5. Creamos el `secret` `AZURE_SUBSCRIPTION_ID`, el valor lo sacamos de la pagina de las **Subscriptions**. Creamos el `secret` `AZURE_TENANT_ID`, el valor lo sacamos de la pagina **TenantProperties**.
9. Una vez configurado todo, podemos hacer `re-run` del `workflow` de la rama `main`, cuando este temrine la aplicación estra desplegada. En el servicio de **Azure** llamado **Container Apps** podremos ver la aplicación y obtener la `URL` de la misma.
10. Probar la app usando `curl -X GET https://<URL>/list` . Debe de retornar una lista vacia.
11. Crear un PR en base de la rama `add-swagger-docs`. Ver que el PR tiene su propio `workflow` que valida los cambios y genera un comentario con el `plan` de **Terraform**.
12. Mergear el PR y ver que la rama `main` vuelve a iniciar un nuevo `workflow` para desplegar la applicación. Cundo termine navegear a la web `https://<URL>/docs` y ver que se abre una interfaz de **Swagger**.

-----

### Docker note

Note that for the sake of example, this project uses a `Dockerfile` and an assembly **JAR**.
That was done mostly for simplicity and since it could be more easily extrapolated to other languages.<br>
For real **Scala** apps, it is usually recommended to use [**sbt-native-packager**](https://github.com/sbt/sbt-native-packager) instead.
