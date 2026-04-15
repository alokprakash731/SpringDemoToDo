# Git Workflow Guide For This Project

This file explains the Git and GitHub concepts used to connect this local project to the existing GitHub repository while preserving the old commit history.

It is written as a practical reference so you can repeat the same process on your own later.

## 1. The Situation We Had

There were two separate things:

- an existing GitHub repository with old commits
- a local project folder with newer code, but no Git history attached to it

That means:

- GitHub already knew about an older version of the project
- your laptop had a newer version of the code
- but the local folder was not yet a Git repository

The goal was:

- connect the local folder to GitHub
- preserve the previous GitHub commit history
- add the current local code on top of that history

## 2. Core Concepts

### Repository

A repository is a project tracked by Git.

A local Git repository contains a hidden `.git` directory.

That `.git` folder stores:

- commit history
- branches
- remote information
- staging information
- metadata

If a project folder has no `.git`, it is just a normal folder, not a Git repo.

### Remote

A remote is a link to another repository, usually on GitHub.

The common name for the main remote is:

- `origin`

Example:

```bash
git remote add origin https://github.com/alokprakash731/SpringDemoToDo.git
```

This means:

- local repo name for the remote is `origin`
- the actual remote repo is the GitHub URL

### Branch

A branch is a named line of development.

Common branch name:

- `main`

You can think of a branch as a moving label that points to the latest commit in that line of work.

### Commit

A commit is a saved snapshot of the project at a point in time.

A commit includes:

- the file changes
- the parent commit(s)
- a commit message
- author information

### Staging Area

Before a commit is created, Git uses a staging area.

`git add` puts changes into that staging area.

`git commit` turns the staged changes into a real commit.

### Fetch

`git fetch` downloads history from GitHub into your local Git database.

Important:

- it does not automatically change your current files
- it updates your knowledge of the remote branches

### Merge

A merge combines two branches or histories together.

If Git can combine them automatically, it does so.
If not, you must resolve merge conflicts manually.

### Push

`git push` uploads your local commits to GitHub.

Important difference:

- `commit` saves locally
- `push` sends those commits to GitHub

## 3. Why We Needed A Special Merge

Your local project folder was not cloned from the existing GitHub repo.

So Git saw:

- one history on GitHub
- one separate history created locally

These are called unrelated histories.

Normally Git blocks merging unrelated histories, because it assumes that may be a mistake.

That is why we used:

```bash
git merge --allow-unrelated-histories local-snapshot
```

That option tells Git:

- yes, these histories are different
- merge them anyway

## 4. What We Did Step By Step

### Step 1: Initialize Git in the local folder

Command:

```bash
git init
```

Meaning:

- create a `.git` directory
- start tracking this folder as a Git repository

### Step 2: Add the GitHub repo as a remote

Command:

```bash
git remote add origin https://github.com/alokprakash731/SpringDemoToDo.git
```

Meaning:

- connect the local repository to the GitHub repository
- call that remote `origin`

### Step 3: Fetch the remote history

Command:

```bash
git fetch origin
```

Meaning:

- download the GitHub branch history
- make `origin/main` available locally

This lets Git know what already exists on GitHub.

### Step 4: Create a safety branch for the current local code

Command:

```bash
git checkout -b local-snapshot
```

Meaning:

- create a new branch named `local-snapshot`
- switch to it immediately

Why this was useful:

- it created a safe place to commit the current local code
- it prevented accidental loss while setting up `main`

### Step 5: Stage the local files

Command:

```bash
git add .
```

Meaning:

- stage all current project files for commit

This does not create a commit yet.
It only prepares the changes.

### Step 6: Commit the local project snapshot

Command:

```bash
git commit -m "Add local Todo API implementation"
```

Meaning:

- create a real commit from the staged files
- save the current local project into Git history

This gave your local code its own commit history anchor.

### Step 7: Create local `main` from the GitHub branch

Command:

```bash
git checkout -B main origin/main
```

Meaning:

- create or reset local `main`
- make it start from `origin/main`

This made local `main` align with the existing GitHub history first.

### Step 8: Merge the local snapshot into `main`

Command:

```bash
git merge --allow-unrelated-histories local-snapshot -m "Merge local Todo API implementation"
```

Meaning:

- merge your `local-snapshot` branch into `main`
- preserve the existing GitHub commit history
- preserve the newer local code
- create a merge commit joining both

### Step 9: Resolve merge conflicts

Some files existed in both histories with different contents.

Examples:

- `.gitignore`
- `build.gradle`
- `settings.gradle`
- Gradle wrapper files

Git marked them as conflicts because it could not safely choose one version automatically.

We resolved those conflicts by keeping the current local project versions for the shared setup files, because those matched the code you are actually using now.

Important idea:

- preserving history does not mean every old file version remains in the final working tree
- it means the old commits remain available in Git history

### Step 10: Complete the merge commit

Commands:

```bash
git add .
git commit --no-edit
```

Meaning:

- stage the conflict resolutions
- finish the merge commit using the existing merge message

### Step 11: Push the combined history to GitHub

Command:

```bash
git push origin main
```

Meaning:

- upload the new local commits to GitHub
- update GitHub `main`

Only after this step do the new commits become visible in the browser on GitHub.

## 5. Why You Could Not See Commits On GitHub Immediately

Because commits were created locally first.

This is the key difference:

- `git commit` saves on your machine
- `git push` uploads to GitHub

Until you push, GitHub has no idea about your new local commits.

## 6. Final History Shape

The final commit graph looked like this:

```text
*   Merge local Todo API implementation
|\
| * Add local Todo API implementation
*  Document git status terms and Added env config
*  Initial commit - SpringDemoToDo project
```

Meaning:

- the bottom commits were the old GitHub history
- one side commit was the local snapshot of your current project
- the top merge commit joined both histories together

This is how the old history was preserved while still adding the newer code.

## 7. Important Commands And What They Mean

### `git init`

Starts Git tracking in the current folder.

### `git remote add origin <repo-url>`

Links the local repo to a GitHub repo.

### `git fetch origin`

Downloads remote branch information and commit history without changing your current files.

### `git checkout -b <branch>`

Creates and switches to a new branch.

### `git checkout -B main origin/main`

Creates or resets local `main` so it starts from `origin/main`.

### `git add .`

Stages all current file changes.

### `git commit -m "message"`

Creates a commit from staged changes.

### `git merge <branch>`

Combines another branch into the current branch.

### `git merge --allow-unrelated-histories <branch>`

Combines two histories that did not originally come from the same repository history.

### `git status`

Shows:

- current branch
- staged changes
- unstaged changes
- untracked files
- ahead/behind information

### `git log --oneline --graph --decorate --all`

Shows the commit history as a graph.

Very useful before pushing.

### `git push origin main`

Uploads the local `main` commits to GitHub.

## 8. Reusable Safe Workflow

If you ever have:

- an existing GitHub repo
- a separate local folder with code
- and you want to preserve the old GitHub history

then this is the safe workflow:

```bash
git init
git remote add origin <repo-url>
git fetch origin
git checkout -b local-snapshot
git add .
git commit -m "Local snapshot"
git checkout -B main origin/main
git merge --allow-unrelated-histories local-snapshot -m "Merge local snapshot"
git push origin main
```

## 9. Useful Checks Before Pushing

Always inspect the state before pushing:

```bash
git status
git log --oneline --graph --decorate --all -n 10
```

This helps you confirm:

- which branch you are on
- whether files are committed
- whether local history looks correct
- whether you are about to preserve history rather than overwrite it

## 10. Practical Rules To Remember

- `commit` is local
- `push` is GitHub
- `fetch` downloads remote history safely
- `merge` combines histories
- branches help you work safely
- unrelated histories need special merge permission
- GitHub browser only shows what has been pushed

## 11. One-Screen Summary

If you forget everything, remember this:

- start Git locally with `git init`
- connect GitHub with `git remote add origin ...`
- fetch remote history with `git fetch origin`
- commit local code on a safety branch
- create local `main` from `origin/main`
- merge the local snapshot into `main`
- resolve conflicts if needed
- push with `git push origin main`

That preserves the old GitHub commits and adds your current local code cleanly.

