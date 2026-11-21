# 1. Employee Payroll Engine
   Warm-up
   A company has three types of employees: Developer, Tester, and Manager.
   Every employee has an ID, name, and basic salary. However, each employee type calculates its bonus differently.
   Requirements
- Create an appropriate parent class.
- Make sure employee IDs and salaries cannot be changed arbitrarily from outside the class.
- Each employee type must implement its own bonus calculation.
- Provide a method to calculate annual compensation.
- Store each employee in a parent-type reference and demonstrate runtime polymorphism.
- Reject invalid salaries.
  Your challenge: Should the parent be a normal class or an abstract class? Should bonus calculation be abstract?
  Test: Create one object of each employee type and calculate their annual compensation without writing a separate calculation branch for every subtype.