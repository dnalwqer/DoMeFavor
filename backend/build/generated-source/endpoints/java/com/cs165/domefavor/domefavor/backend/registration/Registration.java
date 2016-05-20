/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2016-05-04 15:59:39 UTC)
 * on 2016-05-20 at 01:57:41 UTC 
 * Modify at your own risk.
 */

package com.cs165.domefavor.domefavor.backend.registration;

/**
 * Service definition for Registration (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link RegistrationRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Registration extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.22.0 of the registration library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://myApplicationId.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "registration/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public Registration(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Registration(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "listDevices".
   *
   * This request holds the parameters needed by the registration server.  After setting any optional
   * parameters, call the {@link ListDevices#execute()} method to invoke the remote operation.
   *
   * @param count
   * @return the request
   */
  public ListDevices listDevices(java.lang.Integer count) throws java.io.IOException {
    ListDevices result = new ListDevices(count);
    initialize(result);
    return result;
  }

  public class ListDevices extends RegistrationRequest<com.cs165.domefavor.domefavor.backend.registration.model.CollectionResponseRegistrationRecord> {

    private static final String REST_PATH = "registrationrecord/{count}";

    /**
     * Create a request for the method "listDevices".
     *
     * This request holds the parameters needed by the the registration server.  After setting any
     * optional parameters, call the {@link ListDevices#execute()} method to invoke the remote
     * operation. <p> {@link
     * ListDevices#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param count
     * @since 1.13
     */
    protected ListDevices(java.lang.Integer count) {
      super(Registration.this, "GET", REST_PATH, null, com.cs165.domefavor.domefavor.backend.registration.model.CollectionResponseRegistrationRecord.class);
      this.count = com.google.api.client.util.Preconditions.checkNotNull(count, "Required parameter count must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public ListDevices setAlt(java.lang.String alt) {
      return (ListDevices) super.setAlt(alt);
    }

    @Override
    public ListDevices setFields(java.lang.String fields) {
      return (ListDevices) super.setFields(fields);
    }

    @Override
    public ListDevices setKey(java.lang.String key) {
      return (ListDevices) super.setKey(key);
    }

    @Override
    public ListDevices setOauthToken(java.lang.String oauthToken) {
      return (ListDevices) super.setOauthToken(oauthToken);
    }

    @Override
    public ListDevices setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListDevices) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListDevices setQuotaUser(java.lang.String quotaUser) {
      return (ListDevices) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListDevices setUserIp(java.lang.String userIp) {
      return (ListDevices) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Integer count;

    /**

     */
    public java.lang.Integer getCount() {
      return count;
    }

    public ListDevices setCount(java.lang.Integer count) {
      this.count = count;
      return this;
    }

    @Override
    public ListDevices set(String parameterName, Object value) {
      return (ListDevices) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "register".
   *
   * This request holds the parameters needed by the registration server.  After setting any optional
   * parameters, call the {@link Register#execute()} method to invoke the remote operation.
   *
   * @param regId
   * @return the request
   */
  public Register register(java.lang.String regId) throws java.io.IOException {
    Register result = new Register(regId);
    initialize(result);
    return result;
  }

  public class Register extends RegistrationRequest<Void> {

    private static final String REST_PATH = "registerDevice/{regId}";

    /**
     * Create a request for the method "register".
     *
     * This request holds the parameters needed by the the registration server.  After setting any
     * optional parameters, call the {@link Register#execute()} method to invoke the remote operation.
     * <p> {@link
     * Register#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param regId
     * @since 1.13
     */
    protected Register(java.lang.String regId) {
      super(Registration.this, "POST", REST_PATH, null, Void.class);
      this.regId = com.google.api.client.util.Preconditions.checkNotNull(regId, "Required parameter regId must be specified.");
    }

    @Override
    public Register setAlt(java.lang.String alt) {
      return (Register) super.setAlt(alt);
    }

    @Override
    public Register setFields(java.lang.String fields) {
      return (Register) super.setFields(fields);
    }

    @Override
    public Register setKey(java.lang.String key) {
      return (Register) super.setKey(key);
    }

    @Override
    public Register setOauthToken(java.lang.String oauthToken) {
      return (Register) super.setOauthToken(oauthToken);
    }

    @Override
    public Register setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Register) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Register setQuotaUser(java.lang.String quotaUser) {
      return (Register) super.setQuotaUser(quotaUser);
    }

    @Override
    public Register setUserIp(java.lang.String userIp) {
      return (Register) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String regId;

    /**

     */
    public java.lang.String getRegId() {
      return regId;
    }

    public Register setRegId(java.lang.String regId) {
      this.regId = regId;
      return this;
    }

    @Override
    public Register set(String parameterName, Object value) {
      return (Register) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "unregister".
   *
   * This request holds the parameters needed by the registration server.  After setting any optional
   * parameters, call the {@link Unregister#execute()} method to invoke the remote operation.
   *
   * @param regId
   * @return the request
   */
  public Unregister unregister(java.lang.String regId) throws java.io.IOException {
    Unregister result = new Unregister(regId);
    initialize(result);
    return result;
  }

  public class Unregister extends RegistrationRequest<Void> {

    private static final String REST_PATH = "unregisterDevice/{regId}";

    /**
     * Create a request for the method "unregister".
     *
     * This request holds the parameters needed by the the registration server.  After setting any
     * optional parameters, call the {@link Unregister#execute()} method to invoke the remote
     * operation. <p> {@link
     * Unregister#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param regId
     * @since 1.13
     */
    protected Unregister(java.lang.String regId) {
      super(Registration.this, "POST", REST_PATH, null, Void.class);
      this.regId = com.google.api.client.util.Preconditions.checkNotNull(regId, "Required parameter regId must be specified.");
    }

    @Override
    public Unregister setAlt(java.lang.String alt) {
      return (Unregister) super.setAlt(alt);
    }

    @Override
    public Unregister setFields(java.lang.String fields) {
      return (Unregister) super.setFields(fields);
    }

    @Override
    public Unregister setKey(java.lang.String key) {
      return (Unregister) super.setKey(key);
    }

    @Override
    public Unregister setOauthToken(java.lang.String oauthToken) {
      return (Unregister) super.setOauthToken(oauthToken);
    }

    @Override
    public Unregister setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Unregister) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Unregister setQuotaUser(java.lang.String quotaUser) {
      return (Unregister) super.setQuotaUser(quotaUser);
    }

    @Override
    public Unregister setUserIp(java.lang.String userIp) {
      return (Unregister) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String regId;

    /**

     */
    public java.lang.String getRegId() {
      return regId;
    }

    public Unregister setRegId(java.lang.String regId) {
      this.regId = regId;
      return this;
    }

    @Override
    public Unregister set(String parameterName, Object value) {
      return (Unregister) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Registration}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link Registration}. */
    @Override
    public Registration build() {
      return new Registration(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link RegistrationRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setRegistrationRequestInitializer(
        RegistrationRequestInitializer registrationRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(registrationRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
