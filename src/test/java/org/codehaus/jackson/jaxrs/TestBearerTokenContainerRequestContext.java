package org.codehaus.jackson.jaxrs;

public class TestBearerTokenContainerRequestContext extends TestContainerRequestContext{

    public TestBearerTokenContainerRequestContext(String password) {
        super(password);
    }

    public TestBearerTokenContainerRequestContext() {
        super();
        map.add("Authorization", "Bearer eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJSKilKLcsvUtJRyiwuBnIzSkoKiq309SHCmcW5esn5uSDZxBIlK0NTS0sLEwtzcwMdpdSKApiApRlIILE0BW4cTF8tAIL0i9JmAAAA.oam-EjmipSdZLgjpwUsGAJ9YbWGYaTgL0RH1H0VDnIzT0MLM-zv-CoDO3Ha2fG12AmRX13e-3WPYSbd3Go-8CA");
    }

}
