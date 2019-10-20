 # Secure Utils
![Jenkins](https://img.shields.io/jenkins/build/http/trevorism-build.eastus.cloudapp.azure.com/secure-utils)
![Jenkins Coverage](https://img.shields.io/jenkins/coverage/jacoco/http/trevorism-build.eastus.cloudapp.azure.com/secure-utils)
![GitHub last commit](https://img.shields.io/github/last-commit/trevorism/secure-utils)
![GitHub language count](https://img.shields.io/github/languages/count/trevorism/secure-utils)
![GitHub top language](https://img.shields.io/github/languages/top/trevorism/secure-utils)
 
 Latest Version: 1.0.2 
 
## How to Use 
* Add an `@Secure` annotation to controller methods.
* Add a `secure.txt` to the root classpath with the password.
* Requests must use the `Authorization` header with a value which matches `secure.txt`

## How to Build
`gradle clean build`

Note: to get the tests to work, add `secure.txt` with value `'test'` to src/test/resources