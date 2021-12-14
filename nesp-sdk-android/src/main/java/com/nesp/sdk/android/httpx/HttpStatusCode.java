package com.nesp.sdk.android.httpx;

public class HttpStatusCode {

    private static final String TAG = "HttpStatusCode2";

    /**
     * Equivalent to HTTP status 100. System.Net.HttpStatusCode.Continue indicates that
     * the client can continue with its request.
     */
    public static final int CONTINUE = 100;
    /**
     * Equivalent to HTTP status 101. System.Net.HttpStatusCode.SwitchingProtocols indicates
     * that the protocol version or protocol is being changed.
     */
    public static final int SWITCHING_PROTOCOLS = 101;

    /**
     * Equivalent to HTTP status 102. System.Net.HttpStatusCode.Processing indicates
     * that the server has accepted the complete request but hasn't completed it yet.
     */
    public static final int PROCESSING = 102;

    /**
     * Equivalent to HTTP status 103. System.Net.HttpStatusCode.EarlyHints indicates
     * to the client that the server is likely to send a final response with the header
     * fields included in the informational response.
     */
    public static final int EARLY_HINTS = 103;

    /**
     * Equivalent to HTTP status 200. System.Net.HttpStatusCode.OK indicates that the
     * request succeeded and that the requested information is in the response. This
     * is the most common status code to receive.
     */
    public static final int OK = 200;

    /**
     * Equivalent to HTTP status 201. System.Net.HttpStatusCode.Created indicates that
     * the request resulted in a new resource created before the response was sent.
     */
    public static final int CREATED = 201;

    /**
     * Equivalent to HTTP status 202. System.Net.HttpStatusCode.Accepted indicates that
     * the request has been accepted for further processing.
     */
    public static final int ACCEPTED = 202;

    /**
     * Equivalent to HTTP status 203. System.Net.HttpStatusCode.NonAuthoritativeInformation
     * indicates that the returned metainformation is from a cached copy instead of
     * the origin server and therefore may be incorrect.
     */
    public static final int NON_AUTHORITATIVE_INFORMATION = 203;

    /**
     * Equivalent to HTTP status 204. System.Net.HttpStatusCode.NoContent indicates
     * that the request has been successfully processed and that the response is intentionally
     * blank.
     */
    public static final int NO_CONTENT = 204;

    /**
     * Equivalent to HTTP status 205. System.Net.HttpStatusCode.ResetContent indicates
     * that the client should reset (not reload) the current resource.
     */
    public static final int RESET_CONTENT = 205;

    /**
     * Equivalent to HTTP status 206. System.Net.HttpStatusCode.PartialContent indicates
     * that the response is a partial response as requested by a GET request that includes
     * a byte range.
     */
    public static final int PARTIAL_CONTENT = 206;

    /**
     * Equivalent to HTTP status 207. System.Net.HttpStatusCode.MultiStatus indicates
     * multiple status codes for a single response during a Web Distributed Authoring
     * and Versioning (WebDAV) operation. The response body contains XML that describes
     * the status codes.
     */
    public static final int MULTI_STATUS = 207;

    /**
     * Equivalent to HTTP status 208. System.Net.HttpStatusCode.AlreadyReported indicates
     * that the members of a WebDAV binding have already been enumerated in a preceding
     * part of the multistatus response, and are not being included again.
     */
    public static final int ALREADY_REPORTED = 208;

    /**
     * Equivalent to HTTP status 226. System.Net.HttpStatusCode.IMUsed indicates that
     * the server has fulfilled a request for the resource, and the response is a representation
     * of the result of one or more instance-manipulations applied to the current instance.
     */
    public static final int IM_USED = 226;

    /**
     * Equivalent to HTTP status 300. System.Net.HttpStatusCode.Ambiguous indicates
     * that the requested information has multiple representations. The default action
     * is to treat this status as a redirect and follow the contents of the Location
     * header associated with this response. Ambiguous is a synonym for MultipleChoices.
     */
    public static final int AMBIGUOUS = 300;

    /**
     * Equivalent to HTTP status 300. System.Net.HttpStatusCode.MultipleChoices indicates
     * that the requested information has multiple representations. The default action
     * is to treat this status as a redirect and follow the contents of the Location
     * header associated with this response. MultipleChoices is a synonym for Ambiguous.
     */
    public static final int MULTIPLE_CHOICES = 300;

    /**
     * Equivalent to HTTP status 301. System.Net.HttpStatusCode.Moved indicates that
     * the requested information has been moved to the URI specified in the Location
     * header. The default action when this status is received is to follow the Location
     * header associated with the response. When the original request method was POST,
     * the redirected request will use the GET method. Moved is a synonym for MovedPermanently.
     */
    public static final int MOVED = 301;

    /**
     * Equivalent to HTTP status 301. System.Net.HttpStatusCode.MovedPermanently indicates
     * that the requested information has been moved to the URI specified in the Location
     * header. The default action when this status is received is to follow the Location
     * header associated with the response. MovedPermanently is a synonym for Moved.
     */
    public static final int MOVED_PERMANENTLY = 301;

    /**
     * Equivalent to HTTP status 302. System.Net.HttpStatusCode.Found indicates that
     * the requested information is located at the URI specified in the Location header.
     * The default action when this status is received is to follow the Location header
     * associated with the response. When the original request method was POST, the
     * redirected request will use the GET method. Found is a synonym for Redirect.
     */
    public static final int FOUND = 302;

    /**
     * Equivalent to HTTP status 302. System.Net.HttpStatusCode.Redirect indicates that
     * the requested information is located at the URI specified in the Location header.
     * The default action when this status is received is to follow the Location header
     * associated with the response. When the original request method was POST, the
     * redirected request will use the GET method. Redirect is a synonym for Found.
     */
    public static final int REDIRECT = 302;

    /**
     * Equivalent to HTTP status 303. System.Net.HttpStatusCode.RedirectMethod automatically
     * redirects the client to the URI specified in the Location header as the result
     * of a POST. The request to the resource specified by the Location header will
     * be made with a GET. RedirectMethod is a synonym for SeeOther.
     */
    public static final int REDIRECT_METHOD = 303;

    /**
     * Equivalent to HTTP status 303. System.Net.HttpStatusCode.SeeOther automatically
     * redirects the client to the URI specified in the Location header as the result
     * of a POST. The request to the resource specified by the Location header will
     * be made with a GET. SeeOther is a synonym for RedirectMethod
     */
    public static final int SEE_OTHER = 303;

    /**
     * Equivalent to HTTP status 304. System.Net.HttpStatusCode.NotModified indicates
     * that the client's cached copy is up to date. The contents of the resource are
     * not transferred.
     */
    public static final int NOT_MODIFIED = 304;

    /**
     * Equivalent to HTTP status 305. System.Net.HttpStatusCode.UseProxy indicates that
     * the request should use the proxy server at the URI specified in the Location
     * header.
     */
    public static final int USE_PROXY = 305;

    /**
     * Equivalent to HTTP status 306. System.Net.HttpStatusCode.Unused is a proposed
     * extension to the HTTP/1.1 specification that is not fully specified.
     */
    public static final int UNUSED = 306;

    /**
     * Equivalent to HTTP status 307. System.Net.HttpStatusCode.RedirectKeepVerb indicates
     * that the request information is located at the URI specified in the Location
     * header. The default action when this status is received is to follow the Location
     * header associated with the response. When the original request method was POST,
     * the redirected request will also use the POST method. RedirectKeepVerb is a synonym
     * for TemporaryRedirect.
     */
    public static final int REDIRECT_KEEP_VERB = 307;

    /**
     * Equivalent to HTTP status 307. System.Net.HttpStatusCode.TemporaryRedirect indicates
     * that the request information is located at the URI specified in the Location
     * header. The default action when this status is received is to follow the Location
     * header associated with the response. When the original request method was POST,
     * the redirected request will also use the POST method. TemporaryRedirect is a
     * synonym for RedirectKeepVerb.
     */
    public static final int TEMPORARY_REDIRECT = 307;

    /**
     * Equivalent to HTTP status 308. System.Net.HttpStatusCode.PermanentRedirect indicates
     * that the request information is located at the URI specified in the Location
     * header. The default action when this status is received is to follow the Location
     * header associated with the response. When the original request method was POST,
     * the redirected request will also use the POST method.
     */
    public static final int PERMANENT_REDIRECT = 308;

    /**
     * Equivalent to HTTP status 400. System.Net.HttpStatusCode.BadRequest indicates
     * that the request could not be understood by the server. System.Net.HttpStatusCode.BadRequest
     * is sent when no other error is applicable, or if the exact error is unknown or
     * does not have its own error code.
     */
    public static final int BAD_REQUEST = 400;

    /**
     * Equivalent to HTTP status 401. System.Net.HttpStatusCode.Unauthorized indicates
     * that the requested resource requires authentication. The WWW-Authenticate header
     * contains the details of how to perform the authentication.
     */
    public static final int UNAUTHORIZED = 401;

    /**
     * Equivalent to HTTP status 402. System.Net.HttpStatusCode.PaymentRequired is reserved
     * for future use.
     */
    public static final int PAYMENT_REQUIRED = 402;

    /**
     * Equivalent to HTTP status 403. System.Net.HttpStatusCode.Forbidden indicates
     * that the server refuses to fulfill the request.
     */
    public static final int FORBIDDEN = 403;

    /**
     * Equivalent to HTTP status 404. System.Net.HttpStatusCode.NotFound indicates that
     * the requested resource does not exist on the server.
     */
    public static final int NOT_FOUND = 404;

    /**
     * Equivalent to HTTP status 405. System.Net.HttpStatusCode.MethodNotAllowed indicates
     * that the request method (POST or GET) is not allowed on the requested resource.
     */
    public static final int METHOD_NOT_ALLOWED = 405;

    /**
     * Equivalent to HTTP status 406. System.Net.HttpStatusCode.NotAcceptable indicates
     * that the client has indicated with Accept headers that it will not accept any
     * of the available representations of the resource.
     */
    public static final int NOT_ACCEPTABLE = 406;

    /**
     * Equivalent to HTTP status 407. System.Net.HttpStatusCode.ProxyAuthenticationRequired
     * indicates that the requested proxy requires authentication. The Proxy-authenticate
     * header contains the details of how to perform the authentication.
     */
    public static final int PROXY_AUTHENTICATION_REQUIRED = 407;

    /**
     * Equivalent to HTTP status 408. System.Net.HttpStatusCode.RequestTimeout indicates
     * that the client did not send a request within the time the server was expecting
     * the request.
     */
    public static final int REQUEST_TIMEOUT = 408;

    /**
     * Equivalent to HTTP status 409. System.Net.HttpStatusCode.Conflict indicates that
     * the request could not be carried out because of a conflict on the server.
     */
    public static final int CONFLICT = 409;

    /**
     * Equivalent to HTTP status 410. System.Net.HttpStatusCode.Gone indicates that
     * the requested resource is no longer available.
     */
    public static final int GONE = 410;

    /**
     * Equivalent to HTTP status 411. System.Net.HttpStatusCode.LengthRequired indicates
     * that the required Content-length header is missing.
     */
    public static final int LENGTH_REQUIRED = 411;

    /**
     * Equivalent to HTTP status 412. System.Net.HttpStatusCode.PreconditionFailed indicates
     * that a condition set for this request failed, and the request cannot be carried
     * out. Conditions are set with conditional request headers like If-Match, If-None-Match,
     * or If-Unmodified-Since.
     */
    public static final int PRECONDITION_FAILED = 412;

    /**
     * Equivalent to HTTP status 413. System.Net.HttpStatusCode.RequestEntityTooLarge
     * indicates that the request is too large for the server to process.
     */
    public static final int REQUEST_ENTITY_TOO_LARGE = 413;

    /**
     * Equivalent to HTTP status 414. System.Net.HttpStatusCode.RequestUriTooLong indicates
     * that the URI is too long.
     */
    public static final int REQUEST_URI_TOO_LONG = 414;

    /**
     * Equivalent to HTTP status 415. System.Net.HttpStatusCode.UnsupportedMediaType
     * indicates that the request is an unsupported type.
     */
    public static final int UNSUPPORTED_MEDIA_TYPE = 415;

    /**
     * Equivalent to HTTP status 416. System.Net.HttpStatusCode.RequestedRangeNotSatisfiable
     * indicates that the range of data requested from the resource cannot be returned,
     * either because the beginning of the range is before the beginning of the resource,
     * or the end of the range is after the end of the resource.
     */
    public static final int REQUESTED_RANGE_NOT_SATISFIABLE = 416;

    /**
     * Equivalent to HTTP status 417. System.Net.HttpStatusCode.ExpectationFailed indicates
     * that an expectation given in an Expect header could not be met by the server.
     */
    public static final int EXPECTATION_FAILED = 417;

    /**
     * Equivalent to HTTP status 421. System.Net.HttpStatusCode.MisdirectedRequest indicates
     * that the request was directed at a server that is not able to produce a response.
     */
    public static final int MISDIRECTED_REQUEST = 421;

    /**
     * Equivalent to HTTP status 422. System.Net.HttpStatusCode.UnprocessableEntity
     * indicates that the request was well-formed but was unable to be followed due
     * to semantic errors.
     */
    public static final int UNPROCESSABLE_ENTITY = 422;

    /**
     * Equivalent to HTTP status 423. System.Net.HttpStatusCode.Locked indicates that
     * the source or destination resource is locked.
     */
    public static final int LOCKED = 423;

    /**
     * Equivalent to HTTP status 424. System.Net.HttpStatusCode.FailedDependency indicates
     * that the method couldn't be performed on the resource because the requested action
     * depended on another action and that action failed.
     */
    public static final int FAILED_DEPENDENCY = 424;

    /**
     * Equivalent to HTTP status 426. System.Net.HttpStatusCode.UpgradeRequired indicates
     * that the client should switch to a different protocol such as TLS/1.0.
     */
    public static final int UPGRADE_REQUIRED = 426;

    /**
     * Equivalent to HTTP status 428. System.Net.HttpStatusCode.PreconditionRequired
     * indicates that the server requires the request to be conditional.
     */
    public static final int PRECONDITION_REQUIRED = 428;

    /**
     * Equivalent to HTTP status 429. System.Net.HttpStatusCode.TooManyRequests indicates
     * that the user has sent too many requests in a given amount of time.
     */
    public static final int TOO_MANY_REQUESTS = 429;

    /**
     * Equivalent to HTTP status 431. System.Net.HttpStatusCode.RequestHeaderFieldsTooLarge
     * indicates that the server is unwilling to process the request because its header
     * fields (either an individual header field or all the header fields collectively)
     * are too large.
     */
    public static final int REQUEST_HEADER_FIELDS_TOO_LARGE = 431;

    /**
     * Equivalent to HTTP status 451. System.Net.HttpStatusCode.UnavailableForLegalReasons
     * indicates that the server is denying access to the resource as a consequence
     * of a legal demand.
     */
    public static final int UNAVAILABLE_FOR_LEGAL_REASONS = 451;

    /**
     * Equivalent to HTTP status 500. System.Net.HttpStatusCode.InternalServerError
     * indicates that a generic error has occurred on the server.
     */
    public static final int INTERNAL_SERVER_ERROR = 500;

    /**
     * Equivalent to HTTP status 501. System.Net.HttpStatusCode.NotImplemented indicates
     * that the server does not support the requested function.
     */
    public static final int NOT_IMPLEMENTED = 501;

    /**
     * Equivalent to HTTP status 502. System.Net.HttpStatusCode.BadGateway indicates
     * that an intermediate proxy server received a bad response from another proxy
     * or the origin server.
     */
    public static final int BAD_GATEWAY = 502;

    /**
     * Equivalent to HTTP status 503. System.Net.HttpStatusCode.ServiceUnavailable indicates
     * that the server is temporarily unavailable, usually due to high load or maintenance.
     */
    public static final int SERVICE_UNAVAILABLE = 503;

    /**
     * Equivalent to HTTP status 504. System.Net.HttpStatusCode.GatewayTimeout indicates
     * that an intermediate proxy server timed out while waiting for a response from
     * another proxy or the origin server.
     */
    public static final int GATEWAY_TIMEOUT = 504;

    /**
     * Equivalent to HTTP status 505. System.Net.HttpStatusCode.HttpVersionNotSupported
     * indicates that the requested HTTP version is not supported by the server.
     */
    public static final int HTTP_VERSION_NOT_SUPPORTED = 505;

    /**
     * Equivalent to HTTP status 506. System.Net.HttpStatusCode.VariantAlsoNegotiates
     * indicates that the chosen variant resource is configured to engage in transparent
     * content negotiation itself and, therefore, isn't a proper endpoint in the negotiation
     * process.
     */
    public static final int VARIANT_ALSO_NEGOTIATES = 506;

    /**
     * Equivalent to HTTP status 507. System.Net.HttpStatusCode.InsufficientStorage
     * indicates that the server is unable to store the representation needed to complete
     * the request.
     */
    public static final int INSUFFICIENT_STORAGE = 507;

    /**
     * Equivalent to HTTP status 508. System.Net.HttpStatusCode.LoopDetected indicates
     * that the server terminated an operation because it encountered an infinite loop
     * while processing a WebDAV request with "Depth: infinity". This status code is
     * meant for backward compatibility with clients not aware of the 208 status code
     * System.Net.HttpStatusCode.AlreadyReported appearing in multistatus response bodies.
     */
    public static final int LOOP_DETECTED = 508;

    /**
     * Equivalent to HTTP status 510. System.Net.HttpStatusCode.NotExtended indicates
     * that further extensions to the request are required for the server to fulfill
     * it.
     */
    public static final int NOT_EXTENDED = 510;

    /**
     * Equivalent to HTTP status 511. System.Net.HttpStatusCode.NetworkAuthenticationRequired
     * indicates that the client needs to authenticate to gain network access; it's
     * intended for use by intercepting proxies used to control access to the network.
     */
    public static final int NETWORK_AUTHENTICATION_REQUIRED = 511;

    public static final int USER_REJECTED = 600;

}
