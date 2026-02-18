# Git Status Notes

This note explains the terms shown in a typical `git status` output and the
core Git concepts behind them.

## Core Git Model

Git compares three layers:

- **HEAD**: the last commit snapshot.
- **Index (staging area)**: the next commit snapshot being prepared.
- **Working tree**: your current files on disk.

`git status` reports differences between these layers.

## Branch and Remote Tracking

- **On branch `main`**: your `HEAD` points to the local branch `main`.
- **`origin/main`**: a remote-tracking branch (cached view of the remote).
- **Up to date**: local `main` and `origin/main` point to the same commit hash.

## Change Types

- **Changes not staged for commit**: working tree differs from the index.
- **modified:** a tracked file changed in the working tree, not staged.
- **deleted:** a tracked file missing from the working tree, not staged.
- **Untracked files**: files not in the index and not ignored.

## Common Commands Mentioned by Git

- `git add <file>`: copy working-tree content into the index (stage changes).
- `git rm <file>`: remove file and stage the deletion.
- `git restore <file>`: reset working-tree file to match the index or `HEAD`.
- `git commit -a`: stage modified/deleted tracked files, then commit.

## Files Seen in the Status Output

- `.gitignore`: patterns for files Git should ignore.
- `build.gradle`: Gradle build script (plugins, deps, tasks).
- `gradle/wrapper/gradle-wrapper.properties`: Gradle version URL/config.
- `src/main/java/.../SpringDemoToDoApplication.java`: Spring Boot entry point.
- `src/main/resources/application.properties` / `application.yml`:
  Spring Boot configuration files (key/value vs YAML).
- `.DS_Store`: macOS Finder metadata, usually ignored.

## Quick Mental Model

```
HEAD (last commit) <---> Index (staging) <---> Working tree (files)
```

If you edit files, the working tree changes.
If you stage changes, the index updates.
If you commit, HEAD advances.
