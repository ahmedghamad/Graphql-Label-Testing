Feature: Read Label on Github repository

  Background:
    Given I have a valid GitHub token

      # Happy Path
Scenario: Successfully view an existing label
    When I view the labels
    Then I should get status code of "200"
    And I should see  no error messages
    And I should see the  color
    And I should see the  createdAt
    And I should see the description
    And I should see the id
    And I should see the name
    And I should see the url

  # Sad Path
  Scenario: UnSuccessfully view of a label
    When I UnSuccessfully the labels
    Then Check status code of "200"
    And I should see  an error messages











