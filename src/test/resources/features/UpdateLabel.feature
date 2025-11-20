Feature: Update Label

  Background:
    Given an label exists for update

  Scenario: Updating an label
    When I update the issue
    Then I should see the updated title
    And I should return the label to it's original state

#  Scenario:
#    Then
