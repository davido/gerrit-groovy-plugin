Gerrit Groovy Plugin(s)
=======================

This repository contains Gerrit plugins written in Groovy.

To deploy, drop a file in `$gerrit_site/plugins` directory, done.

Review Plugin
-------------

List existing commands:

```
ssh gerrit review

ssh gerrit review
Available commands of review are:

   approve
   dislike
   recommend
   reject

See 'review COMMAND --help' for more information.
```

Approve change:

```
ssh gerrit review approve I59302cbb
Approve change: I59302cbb
```

Projects Plugin
---------------

List existing commands:

```
ssh gerrit projects

Available commands of projects are:

   create   

See 'projects COMMAND --help' for more information.
```

Create project:

```
ssh gerrit projects create foo
Project created: foo
```
