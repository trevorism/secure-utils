 # Secure Utils
![Build](https://github.com/trevorism/secure-utils/actions/workflows/build.yml/badge.svg)
![GitHub last commit](https://img.shields.io/github/last-commit/trevorism/secure-utils)
![GitHub language count](https://img.shields.io/github/languages/count/trevorism/secure-utils)
![GitHub top language](https://img.shields.io/github/languages/top/trevorism/secure-utils)

Secure Utils is the security jar for [trevorism.com](https://trevorism.com)

Latest [Version](https://github.com/trevorism/secure-utils/releases/latest)

## How to Use 
* Add an `@Secure` annotation to controller methods.

```
@Secure(Roles.USER) //Bearer token authorization for users 
@Secure(Roles.ADMIN) //Bearer token authorization for admin users
@Secure(Roles.SYSTEM) //Bearer token authorization for apps.
@Secure(value = Roles.USER, authorizeAudience = true, allowInternal = true, permissions = "crd") //Allows internal tokens made specifically for this audience. The to
```

## How to Build
`gradle clean build`

## How to release
`gradle publishRelease`