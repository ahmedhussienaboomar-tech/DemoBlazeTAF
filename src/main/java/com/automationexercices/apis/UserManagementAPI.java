package com.automationexercices.apis;

import com.automationexercices.utils.logs.LogsManager;
import com.automationexercices.validations.Validation;
import com.automationexercices.validations.Verification;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class UserManagementAPI {
    RequestSpecification requestSpecification;
    Response response;
    Verification verification;
    Validation validation;

    //Constructor
    public UserManagementAPI(){
        requestSpecification = RestAssured.given();
        // given
        //هي اللي بترجعلي ال requestSpecification
        verification = new Verification();

    }

    //endpoints
    private static final String createAccount_endpoint = "/createAccount";
    private static final String deleteAccount_endpoint = "/deleteAccount";
//    private static final String updateAccount_endpoint = "";

    //api methods

    //Request Parameters: name, email, password, title (for example: Mr, Mrs, Miss), birth_date, birth_month, birth_year, firstname, lastname, company, address1, address2, country, zipcode, state, city, mobile_number

    @Step("Create user account with full details")
    public UserManagementAPI createRegisterUserAccount(String name, String email, String password, String title,
                                                       String birth_day, String birth_month, String birth_year,
                                                       String firstname, String lastname, String company, String address1,
                                                       String address2, String country, String zipcode, String state,
                                                       String city, String mobile_number)
    {
        //  هنا استخدمت HashMap علشان ال map العاديه اخرها 10 keys
        Map<String,String> formParams = new HashMap<>();
        formParams.put("name", name);
        formParams.put("email", email);
        formParams.put("password", password);
        formParams.put("title", title);
        formParams.put("birth_date", birth_day);
        formParams.put("birth_month", birth_month);
        formParams.put("birth_year", birth_year);
        formParams.put("firstname", firstname);
        formParams.put("lastname", lastname);
        formParams.put("company", company);
        formParams.put("address1", address1);
        formParams.put("address2", address2);
        formParams.put("country", country);
        formParams.put("zipcode", zipcode);
        formParams.put("state", state);
        formParams.put("city", city);
        formParams.put("mobile_number", mobile_number);
        response = requestSpecification.spec(Builder.getUserManagementPequestSpecification(formParams))
                .post(createAccount_endpoint);
        //عملتله save فال response علشان لما اجي أ validate
        LogsManager.info("Response Body: " + response.asPrettyString());
        return this;
    }


    @Step("Create user account with minimal details")
    public UserManagementAPI createRegisterUserAccount(String name, String email, String password,
                                                       String firstname, String lastname)
    {
        Map<String,String> formParams = new HashMap<>();
        formParams.put("name", name);
        formParams.put("email", email);
        formParams.put("password", password);
        formParams.put("title", "Mr");
        formParams.put("birth_date", "1");
        formParams.put("birth_month", "Junuary");
        formParams.put("birth_year", "1990");
        formParams.put("firstname", firstname);
        formParams.put("lastname", lastname);
        formParams.put("company", "company");
        formParams.put("address1", "address1");
        formParams.put("address2", "address2");
        formParams.put("country", "India");
        formParams.put("zipcode", "123456");
        formParams.put("state", "state");
        formParams.put("city", "city");
        formParams.put("mobile_number", "1234567890");
        response = requestSpecification.spec(Builder.getUserManagementPequestSpecification(formParams))
                .post(createAccount_endpoint);
        //عملتله save فال response علشان لما اجي أ validate
        LogsManager.info("Response Body: " + response.asPrettyString());
        return this;
    }

    //Delete Account
    @Step("Delete user account")
    public UserManagementAPI deleteAccount(String email , String password) {
        Map<String,String> formParams = new HashMap<>();
        formParams.put("email", email);
        formParams.put("password", password);
        response = requestSpecification.spec(Builder.getUserManagementPequestSpecification(formParams))
                .delete(deleteAccount_endpoint);
        LogsManager.info("Response Body: " + response.asPrettyString());
        return this;
    }


    // Validation
    @Step("Verify that user is created successfully")
    public UserManagementAPI verifyUserCreatedSuccessfully() {
        verification.Equals(response.jsonPath().get("message"), "User created!"
                , "User is not created successfully"/* دي اللي هتظهر مثلا لو الاوللى فشلت*/ );
        return this;

    }

    @Step("Verify that user is deleted successfully")
    public UserManagementAPI verifyUserDeletedSuccessfully() {
        verification.Equals(response.jsonPath().get("message"), "Account deleted!"
                , "User is not deleted successfully"/* دي اللي هتظهر مثلا لو الاوللى فشلت*/ );
        return this;
    }

}
