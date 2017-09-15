Feature: Dog Endpoint

  Scenario: The user wants information about a specified dog
    Given that there are 2 dogs in the database
    When the dog endpoint is requested with a valid ID
    Then the response status code is 200
    And the response contains 1 dog

  Scenario: The user wants information about all the dogs
    Given that there are 2 dogs in the database
    When the dog endpoint is requested
    Then the response status code is 200
    And the response contains 2 dogs

  Scenario: The user wants to update the information about a dog
    Given that there are 2 dogs in the database
    When the dog endpoint is requested with a valid ID and updated dog information
    Then the response status code is 200
    And the dog has been updated
    And the database contains 2 dogs

  Scenario: The user wants to add another dog to the list of dogs
    Given that there are 2 dogs in the database
    When a user gives us data about a new dog
    Then the response status code is 200
    And the new dog is added to the database
    And the database contains 3 dogs

  Scenario: The user want to delete a dog from the database
    Given that there are 3 dogs in the database
    When a user deletes a dog with a valid ID
    Then the response status code is 200
    And the dog has been deleted
    And the database contains 2 dogs

  Scenario: The user gives an null ID when trying to request a dog
    Given that there are 2 dogs in the database
    When the dog endpoint is requested with a invalid ID
    Then the response status code is 404

  Scenario: The user gives an invalid ID when trying to request a dog
    Given that there are 2 dogs in the database
    When the dog endpoint is requested with a null ID
    Then the response status code is 404

  Scenario: The user wants to update the information about a dog but gives a null ID
    Given that there are 2 dogs in the database
    When the dog endpoint is requested with a null ID and updated dog information
    Then the response status code is 404

  Scenario: The user wants to update the information about a dog but gives a invalid ID
    Given that there are 2 dogs in the database
    When the dog endpoint is requested with a invalid ID and updated dog information
    Then the response status code is 404

  Scenario: The user wants to delete a dog but gives a invalid ID
    Given that there are 2 dogs in the database
    When the user attempts to delete a dog with a invalid ID
    Then the response status code is 404

  Scenario: The user wants to delete a dog but gives a null ID
    Given that there are 2 dogs in the database
    When the user attempts to delete a dog with a null ID
    Then the response status code is 404