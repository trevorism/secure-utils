 # Secure Utils
 
Add an `@Secure` annotation to controller methods.

Add a `secure.txt` to the root classpath with the allowable password.

Requests must use the `Authorization` header with a value which matches `secure.txt`

To get the tests to work, add `secure.txt` with value `'test'` to `src/test/resources`
