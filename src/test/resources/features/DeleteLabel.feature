Feature: Delete a label from a GitHub repository

  Scenario: Successfully delete an existing label
    Given I have a valid GitHub token
    And I create a new label to delete
    When I send a deleteLabel mutation
    Then the label should be removed from the repository

  Scenario: Delete a non-existent label should return an error
    Given I have a valid GitHub token
    And I have a non-existent label id
    When I send a deleteLabel mutation
    Then the API should return an error message

  Scenario: Delete a label with an invalid token should fail
    Given I have an invalid GitHub token
    And I create a new label to delete
    When I send a deleteLabel mutation
    Then the API should reject the request
