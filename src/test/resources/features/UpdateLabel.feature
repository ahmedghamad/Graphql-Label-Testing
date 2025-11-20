Feature: Update Label

  Background:
    Given an label exists for update

  Scenario: Updating an label
    When I update the issue
    Then I should a status code of 200
    And I should see the no error messages
    And I should see the updated title
    And I should see the updated body
    And I should see it has been updated today
    And I should see the url has been updated
    And I should return the label to it's original state
