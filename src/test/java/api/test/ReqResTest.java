package api.test;

import api.pojo.*;
import api.specs.Specifications;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static api.data.TestData.*;
import static io.restassured.RestAssured.given;

public class ReqResTest {

    /**
     * 1. Используя сервис https://reqres.in/ получить список пользователей со второй (2) страницы;
     * 2. Убедиться, что имена файлов-аватаров пользователей совпадают;
     * 3. Убедиться, что email пользователей имеет окончание reqres.in.
     */
    @Test
    public void checkAvatarAndIdTest() {
        Specifications.installSpecification(Specifications.requestSpec(API_URL), Specifications.responseSpecOk200());
        List<UserData> users = given()
                .when()
                .queryParam(PAGE, 2)
                .get(API + USERS)
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);
        users.forEach(x -> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));
        Assert.assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));
    }

    /**
     * 1. Используя сервис https://reqres.in/ протестировать регистрацию пользователя в системе
     * 2. Создать тест с успешной регистрацией
     */
    @Test
    public void successfulRegistrationTest() {
        Specifications.installSpecification(Specifications.requestSpec(API_URL), Specifications.responseSpecOk200());
        Registration user = new Registration(API_EMAIL, API_PASSWORD);
        SuccessfulRegistration successReg = given()
                .body(user)
                .when()
                .post(API + REGISTER)
                .then().log().all()
                .extract().as(SuccessfulRegistration.class);
        Assert.assertEquals(API_ID, successReg.getId());
        Assert.assertEquals(API_TOKEN, successReg.getToken());
    }

    /**
     * 1. Используя сервис https://reqres.in/ протестировать регистрацию пользователя в системе
     * 2. Создать тест с регистрацией с ошибкой из-за отсутствия пароля
     */
    @Test
    public void unsuccessfulRegistrationTest(){
        Specifications.installSpecification(Specifications.requestSpec(API_URL), Specifications.responseSpecError400());
        Registration user = new Registration(API_EMAIL, "");
        UnsuccessfulRegistration unsuccessfulReg = given()
                .body(user)
                .when()
                .post(API + REGISTER)
                .then().log().all()
                .extract().as(UnsuccessfulRegistration.class);
        Assert.assertEquals("Missing password", unsuccessfulReg.getError());
    }

    /**
     * 1. Используя сервис https://reqres.in/ убедиться, что операция LIST<RESOURCE> возвращает данные, отсортированные по годам
     */
    @Test
    public void sortedYearsTest(){
        Specifications.installSpecification(Specifications.requestSpec(API_URL), Specifications.responseSpecOk200());
        List<ColorsData> colors = given()
                .when()
                .get(API + UNKNOWN)
                .then().log().all()
                .extract().body().jsonPath().getList("data", ColorsData.class);
        List<Integer> years = colors.stream().map(ColorsData::getYear).collect(Collectors.toList());
        List<Integer> sortedYears = years.stream().sorted().collect(Collectors.toList());
        Assert.assertEquals(sortedYears, years);
    }

    /**
     * 1. Используя сервис https://reqres.in/ удалить второго пользователя и сравнить статус-код
     */
    @Test
    public void deleteUserTest(){
        Specifications.installSpecification(Specifications.requestSpec(API_URL), Specifications.responseSpecUnique(204));
        given()
                .when()
                .delete(API + USERS + 2)
                .then().log().all();
    }
}
