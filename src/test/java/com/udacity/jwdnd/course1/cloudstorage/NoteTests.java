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
public class NoteTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    public static String BASEURL;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    public final static String TAG_ = "NoteTests";

    private SignupPage signupPage;
    private ResultPage resultPage;
    private NotePage notePage;
    LoginPage loginPage;

    @BeforeAll
    static void beforeAll() throws InterruptedException {
        Locale.setDefault(new Locale("en","US"));
        WebDriverManager.chromedriver().setup();

    }

    @BeforeEach
    public void beforeEach() throws InterruptedException {
        BASEURL = TestConstant.LOCALHOST + port;
        driver = new ChromeDriver();
        driver.get(NoteTests.BASEURL+
                TestConstant.SIGNUP_URL);

        signupPage = new SignupPage(driver);
        notePage = new NotePage(driver);
        resultPage = new ResultPage(driver);

        signupPage.signup(TestConstant.FIRST_NAME, TestConstant.LAST_NAME, TestConstant.USERNAME, TestConstant.PASSWORD);
        Thread.sleep(3000);

        driver.get(NoteTests.BASEURL+
                TestConstant.LOGIN_URL);
        loginPage = new LoginPage(driver);

        loginPage.login(driver, TestConstant.USERNAME, TestConstant.PASSWORD);
        Thread.sleep(3000);

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
    public void testAddNote() throws InterruptedException {
        String expectedTitle = "title";
        String expectedDescription = "description";
        notePage.addNote(driver, expectedTitle, expectedDescription);

        resultPage.clickOnSuccess(driver);
        notePage.clickNoteTab(driver);
        String resultTitle = notePage.getDisplayTitle(driver);
        String resultDes = notePage.getDisplayDes(driver);
        Assertions.assertEquals(expectedTitle, resultTitle);
        Assertions.assertEquals(expectedDescription, resultDes);
    }

    @Test
    @Order(1)
    public void testDeleteNote() throws InterruptedException {
        notePage.deleteNote(driver);
        resultPage.clickOnSuccess(driver);
        String resultTitle = notePage.getDisplayTitle(driver);
        String resultDes = notePage.getDisplayDes(driver);
        Assertions.assertEquals(null, resultTitle);
        Assertions.assertEquals(null, resultDes);
        String expectedTitle = "title";
        String expectedDescription = "description";
        notePage.clickNoteTab(driver);
        for(int i = 0; i<2; i++) {
            notePage.addNote(driver, expectedTitle, expectedDescription);
            resultPage.clickOnSuccess(driver);
        }
        notePage.deleteNote(driver);
        resultPage.clickOnSuccess(driver);
        notePage.deleteNote(driver);
        resultPage.clickOnSuccess(driver);
        notePage.clickNoteTab(driver);
        resultTitle = notePage.getDisplayTitle(driver);
        resultDes = notePage.getDisplayDes(driver);
        Assertions.assertEquals(null, resultTitle);
        Assertions.assertEquals(null, resultDes);
    }

    @Test
    @Order(3)
    public void testEditNote() throws InterruptedException {
        String expectedTitle = "title";
        String expectedDescription = "description";
        notePage.addNote(driver, expectedTitle, expectedDescription);
        resultPage.clickOnSuccess(driver);
        notePage.clickNoteTab(driver);
        String resultTitle = notePage.getDisplayTitle(driver);
        String resultDes = notePage.getDisplayDes(driver);
        Assertions.assertEquals(expectedTitle, resultTitle);
        Assertions.assertEquals(expectedDescription, resultDes);

        expectedTitle = "new title";
        expectedDescription = "new description";
        notePage.editNote(driver, expectedTitle, expectedDescription);
        resultPage.clickOnSuccess(driver);
        notePage.clickNoteTab(driver);
        resultTitle = notePage.getDisplayTitle(driver);
        resultDes = notePage.getDisplayDes(driver);
        Assertions.assertEquals(expectedTitle, resultTitle);
        Assertions.assertEquals(expectedDescription, resultDes);
    }
}
