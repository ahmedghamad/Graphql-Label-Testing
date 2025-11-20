Feature: GitHub Repository Labels
  As a GitHub user
  I want to manage labels in a repository
  So that I can categorize and organize issues effectively

  Background:
    Given I have a valid GitHub repository ID

  # Happy Path
  Scenario: Create a new label successfully
    Given I have a unique label name "Bug" with color "FF0000" and description "Label for bug issues"
    When I send a createLabel mutation to the GitHub GraphQL API
    Then the API should respond with status code 200
    And no errors message
    And label should have correct details

  Scenario: Create a new label successfully
    Given I have a unique label name "Bug" with color "FF0000" and description "Label for bug issues"
    When I send a createLabel with no description
    Then the API should respond with status code 200
    And no errors message
    And check the labels

  #Sad Path: Invalid repository ID
  Scenario: Fail to create a label with an invalid repository ID
    Given I have a unique label name "InvalidRepo" with color "00FF00" and description "invalid repo test"
    And I set an invalid repository ID
    When I send a createLabel mutation to the GitHub GraphQL API
    Then the API should respond with status code 200
    And the API should return an invalid repository error
    And validate error type is Not_Found


      #Sad Path: missing the mandatory field
  Scenario: Fail to create a label with missing mandatory field
    Given I have a unique label name "missing field" with color "00FF00" and description "missing the field test"
    When I send a createLabel mutation with out the color field to the GitHub GraphQL API
    Then the API should respond with status code 200
    And I get Variable $color of type String! was provided invalid value











