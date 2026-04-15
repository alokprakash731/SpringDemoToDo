# Git Cheatsheet

This is a short everyday reference for common Git commands used in this project.

## Check Current State

```bash
git status
```

Shows:

- current branch
- changed files
- staged files
- untracked files
- whether you are ahead or behind the remote

## See Commit History

```bash
git log --oneline --graph --decorate --all -n 10
```

Shows a short visual history of recent commits and branches.

## Start Git In A Folder

```bash
git init
```

Turns the current folder into a Git repository.

## Connect To GitHub

```bash
git remote add origin <repo-url>
```

Example:

```bash
git remote add origin https://github.com/alokprakash731/SpringDemoToDo.git
```

## Download GitHub History

```bash
git fetch origin
```

Downloads remote history safely without changing your current files.

## Create And Switch To A Branch

```bash
git checkout -b my-branch
```

Example:

```bash
git checkout -b local-snapshot
```

## Stage All Changes

```bash
git add .
```

Prepares all current changes for commit.

## Save A Commit

```bash
git commit -m "Your message"
```

Example:

```bash
git commit -m "Add Todo API"
```

## Merge Another Branch

```bash
git merge branch-name
```

If the histories are unrelated:

```bash
git merge --allow-unrelated-histories branch-name
```

## Push To GitHub

```bash
git push origin main
```

Uploads your local `main` commits to GitHub.

## Pull Latest Changes

```bash
git pull origin main
```

Downloads and merges the latest GitHub changes into local `main`.

## Useful Rule To Remember

- `git commit` = save locally
- `git push` = upload to GitHub
- `git fetch` = download history only
- `git pull` = download and merge

