 # Secure Utils
![Jenkins](https://img.shields.io/jenkins/build/http/trevorism-build.eastus.cloudapp.azure.com/secure-utils)
![Jenkins Coverage](https://img.shields.io/jenkins/coverage/jacoco/http/trevorism-build.eastus.cloudapp.azure.com/secure-utils)
![GitHub last commit](https://img.shields.io/github/last-commit/trevorism/secure-utils)
![GitHub language count](https://img.shields.io/github/languages/count/trevorism/secure-utils)
![GitHub top language](https://img.shields.io/github/languages/top/trevorism/secure-utils)
 
Latest Version: 3.3.0

## Motivation

There should be a simple way to secure JAX-RS endpoints. This jar handles this in the most elegant way possible.

By adding this jar to the classpath, a filter is added which watches for any endpoint with a `@Secure` annotation.
Within the filter, auth validators are applied which validate the request. 

The filter will return a 401 if every validator determines the request is invalid. 

## How to Use 
* Add an `@Secure` annotation to controller methods.

```
@Secure // Legacy implementation: no authorization
@Secure(Roles.USER) //Bearer token authorization for users 
@Secure(Roles.ADMIN) //Bearer token authorization for admin users
@Secure(Roles.SYSTEM) //Bearer token authorization for apps.
```

This library supports both legacy (insecure) authentication and bearer token based authentication and authorization.

Legacy authentication will be removed in version 4.

## Local Development
Since obtaining a valid token is not trivial, there is a LocalhostTokenValidator which expects a file:

secrets.properties, on the root classpath
```properties
localRole=<role>

#where <role> could be  admin | system | user
```

## How to Build
`gradle clean build`

Note: to get the tests to work, add `secure.txt` with value `'test'` to src/test/resources