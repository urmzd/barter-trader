# Barter Trader App - Team 8

### Git Branching Conventions

-   **repo/feature/**: used to create branches for features, improvements and other project-related tasks.
-   **repo/hotfix/**: used to create branches for bugs and other prioritized fixes.

---

### Git Merging Conventions

-   **repo/feature** is typically merged to **repo/develop**.
-   **repo/hotfix** is typically merged to **repo/develop** then **repo/master** thereafter.

---

### Java Coding Conventions

- **Class Names** should follow **UpperCamalCase**
- **Method && Variable Names** should follow **lowerCamalCase**
- **Constant Names** should follow **UPPER_CASE**

---

### Espresso & JUnit

- **Espresso and JUnit Test Method Name** should follow **lowerCamalCase** and identify the desired behaviour.
- **Espresso Test Class Name** should follow **ClassNameInstrumentedTests*. LoginFragment.java --> LoginFragmentInstrumentedTest.java
- **JUnit Test Class Name** should follow **ClassNameUnitTests**. Utils.java --> UtilsUnitTest.java

---

### XML Coding Conventions

- **Resource File Names & Names** should follow **lower_underscore_case** unless it conflicts with the rules below.
- **Style Names** should follow **UpperCamalCase**
- **Layout Names** should follow **container_name**. LoginFragment.java --> fragment_login.xml
- **Resource Element Ids** should follow **name_container_element_name**. Username TextView @ LoginFragment --> @id/login_fragment_text_username
- **Resource Navigation Fragment Ids** should follow **UpperCamalCase**. LoginFragment.java --> @id/LoginFragment.
- **Resource Strings** should follow **type_name**. Below are the **ALLOWED** types.
    - **error**: used to displaying errors.
    - **message**: used to relay information.
    - **actions**: used to describe interactive elements.
    - **title**: used to describe the user interface.

---

