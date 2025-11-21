
Feature: Update Label

  Background:
    Given I set a valid repository ID
    And I have a unique label name "UpdateTest" with color "f100ff" and description "Label for testing updates"
    And I send a createLabel mutation to the GitHub GraphQL API
    And I get the label ID

  @HappyPath
  Scenario: Updating an label
    When I update the label
    Then I should a status code of 200
    And I should see the no error messages
    And I should see the updated title
    And I should see the updated body
    And I should see it has been updated today
    And I should see the url has been updated
    And I delete the label

  @SadPath
  Scenario: Updating an label without name
    When I update the label without the name
    Then I should a status code of 200
    And I should see a invalid value for name error message
    And I delete the label

  @SadPath
  Scenario: Updating an label without ID
    When I update the label without the ID
    Then I should a status code of 200
    And I should see a invalid value for ID error message
    And I delete the label


