Feature: Update Label

  Background:
    Given an label exists for update

  Scenario: Updating an label
    When I update the issue
<<<<<<< HEAD
    Then

  Scenario: Issue is restored afterwards
    Then
=======
    Then I should see the updated title
    And I should return the label to it's original state

#  Scenario:
#    Then
>>>>>>> f949460ff37cde3c58d54843f8dfc7b4ca6f8add
