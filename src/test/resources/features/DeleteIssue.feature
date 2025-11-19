Feature: Delete GitHub Issue

  Background:
    Given an issue exists for deletion

  Scenario: Delete issue returns HTTP 200
    When I delete the issue
    Then the delete response status should be 200

  Scenario: Issue is restored afterwards
    Then the original issue should be restored
