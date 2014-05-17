Simple app that loads tweets but does it with Android `Loader`s and `Service`s
without any line of code that mentions them.

Open source projects used here:
- [Helium](http://github.com/stanfy/helium) for describing/testing REST API and
  generating Java code used for interconnection with Twitter backends
- [Enroscar Content](http://github.com/stanfy/enroscar/tree/master/content) to
  facilitate asynchronous operations managed by Android's Loader
- [Enroscar Goro](http://github.com/stanfy/enroscar/tree/master/goro) to
  enqueue asynchronous operations and handle in Service context

`SampleRxActivity` also demonstrates integration with RxJava.
