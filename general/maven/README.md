# maven

Basic maven knowledge.

## settings.xml (Source: https://maven.apache.org/settings.html)

**settings.xml** is your user preferences. It lives in your main Maven directory (usually $HOME/.m2) and holds your own settings, like listings for non-public repositories, usernames, and other personalized configuration.

**1. Locations**

There are two locations where a settings.xml file may live:

- The Maven install: ${maven.home}/conf/settings.xml

- A user’s install: ${user.home}/.m2/settings.xml

The former **settings.xml** are also called global settings, the latter **settings.xml** are referred to as user settings. If both files exists, their contents gets merged, with the user-specific **settings.xml** being dominant.

**2. Active Profiles**

```xml
<settings ..>
  <activeProfiles>
    <activeProfile>first-profile</activeProfile>
  </activeProfiles>
</settings>
```

The final piece of the **settings.xml** puzzle is the *activeProfiles* element. This contains a set of *activeProfile* elements, which each have a value of a *profile id*. Any *profile id* defined as an *activeProfile* will be active, regardless of any environment settings. If no matching profile is found nothing will happen. For example, if *first-profile* is an *activeProfile*, a profile in a **pom.xml** (or **profile.xml** with a corresponding *id* will be active. If no such profile is found then execution will continue as normal.

## pom.xml (Source: https://maven.apache.org/guides/introduction/introduction-to-the-pom.html)

**pom.xml** is the control file for each Maven project or module. It tells Maven which dependencies the project needs, what processing to apply to build it, and how to package it when it's ready. The POM is part of the project itself, and so information that's necessary to build the project (such as listing which plug-ins to use when building) should go there.
