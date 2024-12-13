-- Insert Topics
insert into topics (title, description, created_at, updated_at) values
('SQL', 'Learn the basics of SQL, from basic queries to advanced joins and optimization techniques.', now(), now()),
('Spring Boot', 'Explore Spring Boot for building Java applications with ease. Topics include REST API, Spring Data, and more.', now(), now()),
('Angular', 'Master Angular, a framework for building single-page web applications. Learn about components, services, routing, etc.', now(), now());

-- Insert Users with hashed passwords
insert into users (email, name, password, created_at, updated_at) values
('john.doe@example.com', 'John Doe', '$2a$10$RydIox19QALP2ngUoNK3euvZP3f3zASIhTa9Fdfqcl92TgnxkKf02', now(), now()),
('jane.smith@example.com', 'Jane Smith', '$2a$10$RydIox19QALP2ngUoNK3euvZP3f3zASIhTa9Fdfqcl92TgnxkKf02', now(), now()),
('alice.jones@example.com', 'Alice Jones', '$2a$10$RydIox19QALP2ngUoNK3euvZP3f3zASIhTa9Fdfqcl92TgnxkKf02', now(), now());

-- Insert Posts with different dates and correct topics
insert into posts (content, title, created_at, updated_at, topic_id, user_id) values
-- Posts for SQL topic
('SQL is a powerful language for managing databases. It is essential for anyone working with data.', 'Introduction to SQL', '2024-11-01 10:00:00', '2024-11-01 10:00:00', 1, 1),
('Mastering SQL JOINs can help you create more complex queries and efficiently retrieve related data.', 'Advanced SQL JOINs', '2024-11-10 12:30:00', '2024-11-10 12:30:00', 1, 2),
('Optimizing SQL queries is key to improving performance, especially for large datasets.', 'SQL Query Optimization', '2024-11-20 14:15:00', '2024-11-20 14:15:00', 1, 3),

-- Posts for Spring Boot topic
('Spring Boot simplifies Java development with built-in configurations and tools like Spring Data JPA.', 'Getting Started with Spring Boot', '2024-11-05 09:45:00', '2024-11-05 09:45:00', 2, 1),
('With Spring Boot, you can easily create REST APIs by adding simple annotations like @RestController.', 'Building a REST API with Spring Boot', '2024-11-12 11:00:00', '2024-11-12 11:00:00', 2, 2),
('Security in Spring Boot is made easy with Spring Security, providing everything from basic authentication to JWT.', 'Securing Spring Boot Applications', '2024-11-18 13:00:00', '2024-11-18 13:00:00', 2, 3),

-- Posts for Angular topic
('Angular is a powerful framework for building dynamic single-page applications using TypeScript.', 'Introduction to Angular', '2024-10-01 08:00:00', '2024-10-01 08:00:00', 3, 1),
('With Angular, you can easily manage state with services and handle routing for a seamless SPA experience.', 'Angular Services and Routing', '2024-10-05 14:30:00', '2024-10-05 14:30:00', 3, 2),
('Angular’s two-way data binding makes it easy to sync model and view components in real time.', 'Mastering Data Binding in Angular', '2024-10-10 16:00:00', '2024-10-10 16:00:00', 3, 3),
('Angular modules help organize your application into cohesive blocks of functionality. Learn how to create them.', 'Understanding Angular Modules', '2024-10-12 17:30:00', '2024-10-12 17:30:00', 3, 1),
('Learn how Angular’s HTTP client allows you to make HTTP requests and interact with REST APIs seamlessly.', 'Using HTTP Client in Angular', '2024-10-15 10:15:00', '2024-10-15 10:15:00', 3, 2),
('Angular’s component-based architecture encourages reusability and maintainability. This is key in large applications.', 'Building Angular Components', '2024-10-18 13:45:00', '2024-10-18 13:45:00', 3, 1),
('Routing in Angular is powerful and easy to set up. It allows you to build complex navigation within your app.', 'Angular Routing and Navigation', '2024-10-20 18:00:00', '2024-10-20 18:00:00', 3, 2),
('Angular Forms allow you to manage user inputs, validate them, and collect the data efficiently.', 'Mastering Forms in Angular', '2024-10-22 09:30:00', '2024-10-22 09:30:00', 3, 3),
('Use Angular’s lifecycle hooks to control the behavior of components at different stages of their existence.', 'Understanding Angular Lifecycle Hooks', '2024-10-25 11:00:00', '2024-10-25 11:00:00', 3, 1),
('Observables are a powerful feature in Angular for managing asynchronous data streams. Learn how to use them.', 'Working with Observables in Angular', '2024-10-28 14:30:00', '2024-10-28 14:30:00', 3, 2),
('Angular Directives provide powerful tools for manipulating the DOM and enhancing HTML functionality.', 'Creating Custom Directives in Angular', '2024-11-02 15:00:00', '2024-11-02 15:00:00', 3, 3),
('Learn about Angular Pipes for transforming data in your templates, and how to create your own custom pipes.', 'Using Angular Pipes', '2024-11-05 12:00:00', '2024-11-05 12:00:00', 3, 1);

-- Insert Comments
insert into comments (content, created_at, updated_at, post_id, user_id) values
-- Comments on SQL Posts
('Great article on SQL! I learned a lot.', now(), now(), 1, 2),
('I didn’t realize how important query optimization was until I read this. Thanks!', now(), now(), 2, 1),
('This post on SQL JOINs helped me understand inner vs outer joins. Very useful.', now(), now(), 3, 3),

-- Comments on Spring Boot Posts
('This Spring Boot tutorial helped me get started with building REST APIs.', now(), now(), 4, 3),
('I found the security tips in Spring Boot very helpful. Looking forward to trying JWT in my project.', now(), now(), 5, 1),
('I was struggling with setting up a REST API, but this guide made it so much easier. Thank you!', now(), now(), 6, 2),
('Awesome insights on Spring Boot security! Will definitely try the JWT example.', now(), now(), 6, 3),

-- Comments on Angular Posts
('I just started using Angular, and this post was a great introduction to components and modules.', now(), now(), 7, 2),
('Angular’s two-way binding is awesome! I used it in my project and it saved me a lot of time.', now(), now(), 8, 1),
('Learning Angular services made my app structure so much clearer. Thanks for the guide.', now(), now(), 9, 3),
('This tutorial helped me a lot with setting up Angular routing. Very well explained!', now(), now(), 7, 1),
('I loved this guide on Angular modules! I’m using them in my app now.', now(), now(), 10, 2),
('Angular HTTP client is so simple to use for making API calls. This article was very helpful.', now(), now(), 11, 3),
('Your component-based approach has helped me create reusable components in my app. Thanks!', now(), now(), 12, 1),
('This post on Angular routing helped me understand lazy loading better. Great work!', now(), now(), 8, 2),
('I was struggling with forms in Angular, but this tutorial really cleared things up.', now(), now(), 9, 1),
('Learning about lifecycle hooks changed the way I manage component states. This post was a game changer.', now(), now(), 10, 3),
('Observables are a lifesaver when dealing with async data. This article made it much easier to understand.', now(), now(), 11, 2),
('Creating custom directives was new to me, but this post helped me get started with them. Thank you!', now(), now(), 12, 3);
