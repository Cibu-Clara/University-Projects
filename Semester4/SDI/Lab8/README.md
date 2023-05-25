# Lab 8 assignment

**Points**: 0.5

**Deadline**: Week 9

**Last chance deadline and penalties**: Week 11, -0.2 points / week delayed

You will need to:
- Implement register and login with username, password and JWT tokens. The username should be unique. The password should have some validation rules to ensure that the password is strong.  
    - Your `User` model must contain any fields needed for login (probably just username and password and whatever else your framework has built in). You should also have a `UserProfile` model with `5` fields with at least 3 validation rules. For example: bio, location, birthday, gender, marital status.  
    - If you already have a `User` model and you use it for this, add one more entity to your app.
    - All of your entities should be directly or indirectly associated with the user that created them. Add `10 000` (ten thousand) random users and randomly associate the existing entities with these users. For testing purposes, have the same password for each of these users. Each entity **must** have an associated user, but not all users must have associated entities. The data insertion part must be implemented in an SQL script. You might want to work on a backup database before running the script on the production database.
    - The `/api/register` endpoint should generate a confirmation code that is valid for `10` minutes. The user must request `/api/register/confirm/<confirmation code>` to activate their account.
- Everything must be validated everywhere possible, including IDs. Implement the `happy case - with data`, `happy case - without data` and `error case` scenarios for all endpoints.
- For all routes that show all entities, also show the username of the user that added the entity. Clicking on the username should take you to the user's profile page.
    - The profile page should contain the user profile info and statistics regarding how many of each entity the user has added.
- You must start using feature branches for all functionalities. Your feature branches should be named according to the feature that you're implementing. Use Pull Requests and merge your branches into a `development` branch when you are done with your work. You can delete the feature branches after that. Have netlify deploy from `development`. You can read more about this here: https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/about-pull-requests
