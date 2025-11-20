Feature: Delete a label from a GitHub repository

  Scenario: Successfully delete an existing label
    Given I have a valid GitHub token
    And I get the id of label "bug" in repository "owner/repo"
    When I send a deleteLabel mutation
    Then the label should be removed from the repository

