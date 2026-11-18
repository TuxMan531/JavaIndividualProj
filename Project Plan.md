# Movie Hunter project plan

The dates below are proposed build targets. November 18 is the catch-up target for the three course checkpoints. The final individual project is due December 16 at 9 p.m. Central.

**Nov 16–17 — Checkpoint 1, design.** Finalize the problem and five MVP stories. Sketch the search, results, sign-in, and watchlist flow. Write the weekly reflection with actual time and progress. Seek instructor approval for the problem, stories, and dated plan. Push the plan, MVP stories, designs, and reflections to this repository. A reviewer can trace the same-title movie example from search to save.

**Nov 17 — Checkpoint 2, persistence.** Create MySQL tables for User, Movie, and WatchlistEntry. Each user and movie can have many watchlist entries. Enforce one entry per user/movie pair. Use seeded films to implement and test the persistence behind MVP 4–5. A Hibernate DAO creates, reads, updates, and deletes entries. JUnit tests query the database after each operation, including duplicate and ownership cases. Use Log4J for diagnostic logging.

**Nov 18 — Checkpoint 3, deployment.** Wire Cognito registration and login to the deployed Java app. Bind each account to its own entries and render saved films on a database-backed JSP. Deploy on AWS. Demonstrate MVP 3 and the saved watchlist at the public URL. Verify persistence after reload and isolation with a second account. Add the deployed URL to the class list.

**Nov 19–25 — Discovery.** Select a metadata provider with title search and stable IDs. Call it from Java, map JSON to movie objects, and connect the result cards to the watchlist. Reuse the Week 8 project exercise implementation if available. Demonstrate MVP 1–2 and save a real result. Test same-title releases, empty results, missing posters, and an unavailable provider.

**Nov 26–Dec 9 — Review and revision.** Test all five stories on phone and desktop. Complete the required design/code reviews with at least two peers and the instructor. Keep weekly reflections. Agree on an independent technology with the instructor. A bounded cache for repeated searches is one candidate. Link feedback to specific revisions. If using a cache, verify that repeated queries avoid redundant provider requests and that expired results refresh.

**Dec 10–16 — Final delivery.** Recheck the public app, generate HTML JavaDoc, publish supporting materials, and prepare the class presentation and public demo video. From the README, a reviewer can reach the app, documentation, and video. The demonstration covers search, save, persistence, ownership, and the independent technology.

**Checkpoint 1 reminder for later.** Once the checkpoint materials are ready, add the URL of this repository to the instructor’s class project list. [Movie Hunter repository](https://github.com/TuxMan531/JavaIndividualProj) · [Class project list](https://github.com/mad-ent-java-f26/student/blob/main/IndividualProjects.md)

[Official checkpoint requirements and rubric](https://paulawaite.com/education/java113/IndividualProject.html#checkpoints)
