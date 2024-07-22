# Possible Enhancements

This solution "works" in the sense that you can make HTTP requests against it and those get routed to the Dummy REST API.
However, the Dummy API has a prohibitive rate limit: what feels like 1 request/minute. I haven't timed it or checked,
but you can't make subsequent requests. Currently, using this API frequently will cause 500 errors, which is not usable
for any real use case.

There are a few measures we can take to mitigate this:

1. Implement exponential backoff for all Dummy API interactions. That way, we can retry after the rate limit is lifted (assuming it had a sane rate limit).
2. Add a caching layer that allows requests to be processed quickly, then add a flushing mechanism to write back to the API.

These aren't mutually exclusive; both can (and probably should in a case like this) be implemented to let end-users
use our API within a reasonable timeframe.