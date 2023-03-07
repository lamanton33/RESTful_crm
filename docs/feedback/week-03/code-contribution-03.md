# Code Contributions and Code Reviews


#### Focused Commits

Grade: Excellent

Feedback: Good amount of commits that affect a small number of files and good specific oneliner commit titles.


#### Isolation

Grade: Good

Feedback: Since this week didn't have a lot of code writing it is a bit early to give feedback on this, but so far it seems that the branches/merge requests do isolate individual problems/features/uploadings which is very good! I did see a commit directly to the main branch - it is good to avoid this in general.


#### Reviewability

Grade: Very Good

Feedback: Latest MRs did have some quite good and meaningful comments which is great to see, keep it up like this in the future.  Also, when there is a minimum of two people approving the MR then two people _other_ than the person making the MR need to approve it - unless of course you have decided as a team that you will need only one approval to do MRs. Tip: you can fill in the gitlab "Assignees", "Reviewers" etc for the MRs for better organization.


#### Code Reviews

Grade: Very Good

Feedback: MRs don't stay open for too long and latest MR comments are very good (Tip: set discussions to resolved once they are resolved). The amount of discussion correlates with the change size and comments are constructive and goal oriented.


#### Build Server

Grade: Sufficient

Feedback: Builds (pipelines) pass so far almost every time which is great, but that is because it is only set up to run the build. Make sure you select 10+ checkstyle rules and add them to your checkstyle.xml (check that the pipeline on gitlab also runs it). I did see some consecutive failed pipelines - generally try to fix the pipeline before implementing more features.
