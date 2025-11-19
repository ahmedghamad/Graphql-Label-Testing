Feature: Update Label

  Background:
    Given an label exists for update

  Scenario: Updating an label
    When I delete the issue
    Then the delete response status should be 200

  Scenario: Issue is restored afterwards
    Then the original issue should be restored
