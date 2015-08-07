# Contributing to Suiton

Please take a moment to review this document in order to make the contribution
process easy and effective for everyone involved.

Following these guidelines helps to communicate that you respect the time of
the developers managing and developing this open source project. In return,
they should reciprocate that respect in addressing your issue or assessing
patches and features.

## Using the issue tracker

The issue tracker is the preferred channel for [bug reports](#bugs),
[features requests](#features) and [submitting pull
requests](#pull-requests), but please respect the following restrictions:

* Please **do not** use the issue tracker for personal support requests.

* Please **do not** derail or troll issues. Keep the discussion on topic and
  respect the opinions of others.


<a name="bugs"></a>
## Bug reports

A bug is a _demonstrable problem_ that is caused by the code in the repository.
Good bug reports are extremely helpful - thank you!

Guidelines for bug reports:

1. **Use the GitHub issue search** &mdash; check if the issue has already been reported. Make sure to search all issues, not only the open issues.

2. **Check if the issue has been fixed** &mdash; try to reproduce it using the latest `master` or development branch in the repository.

3. **Isolate the problem** &mdash; create a [reduced test case]().

A good bug report shouldn't leave others needing to chase you up for more
information. Please try to be as detailed as possible in your report. What is
your environment? What steps will reproduce the issue? What browser(s) and OS
experience the problem? What would you expect to be the outcome? All these
details will help people to fix any potential bugs.


<a name="features"></a>
## Feature requests

Feature requests are welcome. But take a moment to find out whether your idea
fits with the scope and aims of the project. It's up to *you* to make a strong
case to convince the project's developers of the merits of this feature. Please
provide as much detail and context as possible.


<a name="pull-requests"></a>
## Pull requests

Contributing to Suiton is easy:

  * Fork Suiton on http://github.com/suiton2d/suiton
  * Learn how to work with the source
  * Hack away and send a pull request on Github!
  

### API Changes & Additions

If you modify a public API, or add a new one, make sure to add these changes to the CHANGES file in the root of the repository. In addition to the CHANGES file, such modifications are also published on the blog and on Twitter to reach all of the community.


### Code Style

Suiton does not have an official coding standard. We mostly follow the usual Java style, and so should you.

A few things we'd rather not like to see:

* underscores in any kind of identifier
* [Hungarian notation](http://en.wikipedia.org/wiki/Hungarian_notation)
* Prefixes for fields or arguments
* Curlies on new lines

If you modify an existing file, follow the style of the code in there.

If you create a new class, please add at least class documentation that explains the usage and scope of he class. You can omit Javadoc for methods that are self-explanatory.

If your class is explicitly thread-safe, mention it in the Javadoc. The default assumption is that classes are not thread-safe, to reduce the amount of costly locks in the code base.


### Performance Considerations

Suiton is meant to run on both desktop and mobile platforms, including browsers (JavaScript!). While the desktop HotSpot VM can take quite a beating in terms of unnecessary allocations, Dalvik and consorts don't.

A few guidelines:

* Avoid temporary object allocation wherever possible
* Do not make defensive copies
* Avoid locking, Suiton classes are by default not thread-safe unless explicitly specified
* Do not use boxed primitives
* Use the collection classes in the [com.badlogic.gdx.utils package](https://github.com/libgdx/libgdx/tree/master/gdx/src/com/badlogic/gdx/utils)
* Do not perform argument checks for methods that may be called thousands of times per frame
* Use pooling if necessary, if possible, avoid exposing the pooling to the user as it complicates the API


### Git

Most of the Suiton team members are Git novices, as such we are just learning the ropes ourself. To lower the risk of getting something wrong, we'd kindly ask you to keep your pull requests small if possible. A change-set of 3000 files is likely not to get merged.

We do open new branches for bigger API changes. If you help out with a new API, make sure your pull request targets that specific branch.

Pull requests for the master repository will be checked by multiple core contributors before inclusion. We may reject your pull requests to master if we do not deem them to be ready or fitting. Please don't take offense in that case.
