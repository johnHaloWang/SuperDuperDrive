package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.*;
import com.udacity.jwdnd.course1.cloudstorage.utils.TestConstant;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Locale;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CredentialTests {
    @LocalServerPort
    private int port;

    private WebDriver driver;
    public static String BASEURL;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    public final static String TAG_ = "CredentialTests";

    private SignupPage signupPage;
    private ResultPage resultPage;
    private LoginPage loginPage;
    private CredentialPage credentialPage;

    @BeforeAll
    static void beforeAll() throws InterruptedException {
        Locale.setDefault(new Locale("en","US"));
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() throws InterruptedException {
        BASEURL = TestConstant.LOCALHOST + port;
        driver = new ChromeDriver();
        driver.get(CredentialTests.BASEURL+
                TestConstant.SIGNUP_URL);

        signupPage = new SignupPage(driver);
        credentialPage = new CredentialPage(driver);
        resultPage = new ResultPage(driver);

        signupPage.signup(TestConstant.FIRST_NAME, TestConstant.LAST_NAME, TestConstant.USERNAME, TestConstant.PASSWORD);

        driver.get(CredentialTests.BASEURL+
                TestConstant.LOGIN_URL);
        loginPage = new LoginPage(driver);

        loginPage.login(driver, TestConstant.USERNAME, TestConstant.PASSWORD);

    }

    @AfterEach
    public void afterEach() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterAll
    static void aferAll(){

    }

    @Test
    @Order(1)
    public void testAddCred() throws InterruptedException {
        String expectedUsername = "username1";
        String expectedPassword = "password2";
        String expectedUrl = "url1";
        credentialPage.addCredential(driver, expectedUsername, expectedPassword, expectedUrl);

        resultPage.clickOnSuccess(driver);
        credentialPage.clickCredentialTab(driver);
        String resultUsername = credentialPage.getDisplayUsername(driver);
        String resultUrl = credentialPage.getDisplayUrl(driver);
        Assertions.assertEquals(expectedUsername, resultUsername);
        Assertions.assertEquals(expectedUrl, resultUrl);
    }

    @Test
    @Order(2)
    public void TestPassword() throws InterruptedException{
        credentialPage.clickCredentialTab(driver);
        String resultPassword = credentialPage.clickEditBtn(driver);
        String expectedPassword = "password2";
        Assertions.assertEquals(expectedPassword, resultPassword);
    }

    @Test
    @Order(3)
    public void testDelete() throws InterruptedException {
        credentialPage.deleteCredential(driver);
        resultPage.clickOnSuccess(driver);
        credentialPage.clickCredentialTab(driver);
        String resultUrl = credentialPage.getDisplayUrl(driver);
        String resultUsername = credentialPage.getDisplayUsername(driver);
        String resultPassword = credentialPage.getDisplayPassword(driver);
        Assertions.assertEquals(null, resultUrl);
        Assertions.assertEquals(null, resultUsername);
        Assertions.assertEquals(null, resultPassword);
    }

    @Test
    @Order(4)
    public void testEdit() throws InterruptedException {
        String expectedUrl = "title3";
        String expectedPassword = "password3";
        String expectedUsername = "username3";
        credentialPage.addCredential(driver, expectedUsername, expectedPassword, expectedUrl);
        resultPage.clickOnSuccess(driver);
        credentialPage.clickCredentialTab(driver);


        String resultUsername = credentialPage.getDisplayUsername(driver);
        String resultUrl = credentialPage.getDisplayUrl(driver);

        Assertions.assertEquals(expectedUsername, resultUsername);
        Assertions.assertEquals(expectedUrl, resultUrl);

        expectedUrl = "new url";
        expectedPassword = "new password";
        expectedUsername = "new username";

        credentialPage.editCredential(driver, expectedUsername, expectedPassword, expectedUrl);
        resultPage.clickOnSuccess(driver);
        credentialPage.clickCredentialTab(driver);

        resultUsername = credentialPage.getDisplayUsername(driver);
        resultUrl = credentialPage.getDisplayUrl(driver);

        Assertions.assertEquals(expectedUsername, resultUsername);
        Assertions.assertEquals(expectedUrl, resultUrl);

    }
}
