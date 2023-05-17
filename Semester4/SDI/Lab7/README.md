# Lab 7 assignment

**Points**: 0.4

**Deadline**: Week 8

**Last chance deadline and penalties**: Week 10, -0.1 points / week delayed

For this and all future assignments, unless otherwise specified, we are no longer treating the backend and the frontend separately when talking about features: implementing most features will require you to work on both the backend and the frontend, even if this is not explicitly stated.

You will need to:
- Implement all functionalities on the frontend. Implement pagination on the backend and navigation through it on the frontend. You are **not allowed** to **only** use built-in pagination classes and pagination libraries. You must implement the pagination class or functionality yourself either on the frontend or on the backend. Do it in a way that allows for changes. For example, you may use built-in classes on the backend and no built-in classes on the frontend. Your live coding task during the lab will involve the pagination aspect. Recall that you should have at least the following from previous assignments and live tasks:  
    - 3 entities.
    - 1 intermediate entity for a many to many relation, whose CRUDs can be handled either through dedicated routes or through the other entities' routes.
    - 3 validation rules.
    - 1 filter by a numerical field.
    - 2 statistical reports.
- Make sure that your functionalities have efficient implementations. This may require adding indexes to your database, implementing autocomplete and fixing things on the backend. There shouldn't be a noticeable slowdown when interacting with any page.
- Each show all page should display, for each row, an aggregated value on a related entity (for example, the number of students enrolled in the course for the show all courses page). This should still be efficient and not affect page load times too much.
- Duplicate your validation logic on the frontend and add `2` more validation rules, also duplicated on the backend and the frontend. 
- Error messages should show up on the frontend near the corresponding textboxes or using Toasters.
- If you haven't already, add a CSS Components Library to your frontend: Material UI, Bulma, Bootstrap, Tailwind or something similar.
- Secure and improve your server by:
    - Installing `nginx` and a dedicated application server for your backend. The `nginx` reverse proxy server should communicate with your application server and the application server should not be directly exposed to the internet. The application server can be Gunicorn, Apache, Tomcat or something else. For .NET projects, you can just use IIS or the default deployment scheme. If you do not do it this way then you must have a very compelling reason that you can explain with your tech stack's documentation as support.
    - Installing an SSL certificate using something like `certbot` or `acmesh` and `freedns`. Since your VM IP will change if you shut down your VM, I recommend starting and configuring it well in advance of your lab, to account for any possible delays with the DNS propagation. Everything should now use `https`. Make sure you update your frontend accordingly.
    - Making both `nginx` and your application server services that start when your VM starts and that you control with commands such as `sudo systemctl start nginx`, `sudo systemctl start your_app_server`. You might be asked to show this by restarting your VM.