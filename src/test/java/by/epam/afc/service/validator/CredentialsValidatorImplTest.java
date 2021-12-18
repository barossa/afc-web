package by.epam.afc.service.validator;

import by.epam.afc.service.validator.impl.CredentialsValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class CredentialsValidatorImplTest {

    @ParameterizedTest
    @MethodSource("incorrectPasswords")
    public void incorrectPasswordTest(String password){
        CredentialsValidatorImpl validator = CredentialsValidatorImpl.getInstance();
        boolean actual = validator.validatePassword(password);
        Assertions.assertFalse(actual);
    }

    @ParameterizedTest
    @MethodSource("correctPasswords")
    public void correctPasswordTest(String password){
        CredentialsValidatorImpl validator = CredentialsValidatorImpl.getInstance();
        boolean actual = validator.validatePassword(password);
        Assertions.assertTrue(actual);
    }

    @ParameterizedTest
    @MethodSource("incorrectLogins")
    public void incorrectLoginTest(String login){
        CredentialsValidatorImpl validator = CredentialsValidatorImpl.getInstance();
        boolean actual = validator.validateLogin(login);
        Assertions.assertFalse(actual);
    }

    @ParameterizedTest
    @MethodSource("correctLogins")
    public void correctLoginTest(String login){
        CredentialsValidatorImpl validator = CredentialsValidatorImpl.getInstance();
        boolean actual = validator.validateLogin(login);
        Assertions.assertTrue(actual);
    }

    @ParameterizedTest
    @MethodSource("incorrectNames")
    public void incorrectNameTest(String name){
        CredentialsValidatorImpl validator = CredentialsValidatorImpl.getInstance();
        boolean actual = validator.validateName(name);
        Assertions.assertFalse(actual);
    }

    @ParameterizedTest
    @MethodSource("correctNames")
    public void correctNameTest(String name){
        CredentialsValidatorImpl validator = CredentialsValidatorImpl.getInstance();
        boolean actual = validator.validateName(name);
        Assertions.assertTrue(actual);
    }

    @ParameterizedTest
    @MethodSource("incorrectEmails")
    public void incorrectEmailTest(String email){
        CredentialsValidatorImpl validator = CredentialsValidatorImpl.getInstance();
        boolean actual = validator.validateEmail(email);
        Assertions.assertFalse(actual);
    }

    @ParameterizedTest
    @MethodSource("correctEmails")
    public void correctEmailTest(String email){
        CredentialsValidatorImpl validator = CredentialsValidatorImpl.getInstance();
        boolean actual = validator.validateEmail(email);
        Assertions.assertTrue(actual);
    }

    @ParameterizedTest
    @MethodSource("incorrectPhones")
    public void incorrectPhoneTest(String phone){
        CredentialsValidatorImpl validator = CredentialsValidatorImpl.getInstance();
        boolean actual = validator.validatePhone(phone);
        Assertions.assertFalse(actual);
    }

    @ParameterizedTest
    @MethodSource("correctPhones")
    public void correctPhoneTest(String phone){
        CredentialsValidatorImpl validator = CredentialsValidatorImpl.getInstance();
        boolean actual = validator.validatePhone(phone);
        Assertions.assertTrue(actual);
    }

    @ParameterizedTest
    @MethodSource("incorrectAbout")
    public void incorrectAboutTest(String about){
        CredentialsValidatorImpl validator = CredentialsValidatorImpl.getInstance();
        boolean actual = validator.validateAbout(about);
        Assertions.assertFalse(actual);
    }

    @ParameterizedTest
    @MethodSource("correctAbout")
    public void correctAboutTest(String about){
        CredentialsValidatorImpl validator = CredentialsValidatorImpl.getInstance();
        boolean actual = validator.validateAbout(about);
        Assertions.assertTrue(actual);
    }

    @ParameterizedTest
    @MethodSource("incorrectStatuses")
    public void incorrectStatusTest(String status){
        CredentialsValidatorImpl validator = CredentialsValidatorImpl.getInstance();
        boolean actual = validator.validateStatus(status);
        Assertions.assertFalse(actual);
    }

    @ParameterizedTest
    @MethodSource("correctStatuses")
    public void correctStatusTest(String status){
        CredentialsValidatorImpl validator = CredentialsValidatorImpl.getInstance();
        boolean actual = validator.validateStatus(status);
        Assertions.assertTrue(actual);
    }

    @ParameterizedTest
    @MethodSource("incorrectRoles")
    public void incorrectRoleTest(String role){
        CredentialsValidatorImpl validator = CredentialsValidatorImpl.getInstance();
        boolean actual = validator.validateRole(role);
        Assertions.assertFalse(actual);
    }

    @ParameterizedTest
    @MethodSource("correctRoles")
    public void correctRoleTest(String role){
        CredentialsValidatorImpl validator = CredentialsValidatorImpl.getInstance();
        boolean actual = validator.validateRole(role);
        Assertions.assertTrue(actual);
    }

    private static Stream<String> incorrectPasswords(){
        return Stream.of(" bla!",
                "",
                "1234",
                "1234567890123456789987654321123456789",
                " ",
                "qwer ty");
    }

    private static Stream<String> correctPasswords(){
        return Stream.of("12345",
                "qwerty",
                "765ghjs",
                "defaultpassword!",
                "___###password",
                "-_-_-_==");
    }

    private static Stream<String> incorrectLogins(){
        return Stream.of(" bla!",
                "incorrect<login",
                "Boooom!!!",
                " loginWithEscape",
                "itISmyLOGINforTHEcenturies");
    }

    private static Stream<String> correctLogins(){
        return Stream.of("Vlad25",
                "Login0",
                "admin",
                "itISmyLOGIN007",
                "blessYOU");
    }

    private static Stream<String> incorrectNames(){
        return Stream.of("50Cent",
                "O'neill",
                "Uvupvupvelsovieoszazs",
                "My name",
                " ");
    }

    private static Stream<String> correctNames(){
        return Stream.of("Anton",
                "Prokopovich",
                "Pushkin",
                "Alexander",
                "Ivan");
    }

    private static Stream<String> incorrectEmails(){
        return Stream.of("1auto-mail@tut.by",
                "fedovich.mail.ru",
                "support@mail.",
                " sellmaill@yahoo.com",
                "self@mail.1");
    }

    private static Stream<String> correctEmails(){
        return Stream.of("mail@mail.ru",
                "graf.montekristo@gmail.com",
                "admin_support@vk.com",
                "anton@tut.by",
                "student97090000@bsuir.by");
    }

    private static Stream<String> incorrectPhones(){
        return Stream.of("-375291234567",
                "3752912345 ",
                "37544",
                "3753366665548888",
                "_375292844072");
    }

    private static Stream<String> correctPhones(){
        return Stream.of("+375290123456",
                "375290123456",
                "+375445555555",
                "375332222222",
                "+375333555533");
    }

    private static Stream<String> incorrectAbout(){
        return Stream.of(" I'm living in Minsk now.",
                "---Such a beauty city!!!",
                "7777Just do this",
                "For me;;;",
                "Bla bla bla... BLA!<script></script> BLA! BLA!");
    }

    private static Stream<String> correctAbout(){
        return Stream.of("I'm living in Minsk now.",
                "Such a beauty city!!!",
                "Just do this",
                "For me",
                "Bla bla bla... BLA! BLA! BLA!");
    }

    private static Stream<String> incorrectStatuses(){
        return Stream.of("ACTIVATED",
                "DEACTIVATED",
                "IN_BAN");
    }

    private static Stream<String> correctStatuses(){
        return Stream.of("ACTIVE",
                "BANNED",
                "DELAYED_REG");
    }

    private static Stream<String> incorrectRoles(){
        return Stream.of("BOSS",
                "Colonel",
                "SUPERUSER",
                "ROOT",
                "ADMIN");
    }

    private static Stream<String> correctRoles(){
        return Stream.of("GUEST",
                "USER",
                "MODERATOR",
                "ADMINISTRATOR");
    }

}
