Feature: User Journey of using git hub labels


  Scenario: User Journey of creating, reading updating and deleting a label
    Given I set a valid repository ID
    And I have a unique label name "UserJourney" with color "f100ff" and description "Label for user Journey"
    When I send a createLabel mutation to the GitHub GraphQL API
    And I get the label ID
    And I view the labels
    Then I should see the name "UserJourney"
    And I get the created label ID
    And I update the label name to "ChangedLabelName"
    And I view the labels
    And I should see the name "ChangedLabelName"
    And I delete the label

