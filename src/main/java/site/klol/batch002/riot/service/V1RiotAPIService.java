package site.klol.batch002.riot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import site.klol.batch002.riot.RiotURLResolver;
import site.klol.batch002.riot.dto.AccountDTO;

import java.util.List;
import java.util.Optional;

/**
    <h1> Riot API 4xx CODES</h1>
    <pre>
    4XX ERROR CODES
    The 4xx class of error codes is meant to indicate that the client failed to provide a valid request. Below are the most common 4xx class of error codes you might encounter when using the API.

    400 (Bad Request) This error indicates that there is a syntax error in the request and the request has therefore been denied. The client should not continue to make similar requests without modifying the syntax or the requests being made.

    Common Reasons

    A provided parameter is in the wrong format (e.g., a string instead of an integer).
    A provided parameter is invalid (e.g., beginTime and startTime specify a time range that is too large).
    A required parameter was not provided.
    401 (Unauthorized) This error indicates that the request being made did not contain the necessary authentication credentials (e.g., an API key) and therefore the client was denied access. The client should not continue to make similar requests without including an API key in the request.

    Common Reasons

    An API key has not been included in the request.
    403 (Forbidden) This error indicates that the server understood the request but refuses to authorize it. There is no distinction made between an invalid path or invalid authorization credentials (e.g., an API key). The client should not continue to make similar requests.

    Common Reasons

    An invalid API key was provided with the API request.
    A blacklisted API key was provided with the API request.
    The API request was for an incorrect or unsupported path.
    404 (Not Found) This error indicates that the server has not found a match for the API request being made. No indication is given whether the condition is temporary or permanent.

    Common Reasons

    The ID or name provided does not match any existing resource (e.g., there is no Summoner matching the specified ID).
    There are no resources that match the parameters specified.
    415 (Unsupported Media Type) This error indicates that the server is refusing to service the request because the body of the request is in a format that is not supported.

    Common Reasons

    The Content-Type header was not appropriately set.
    429 (Rate Limit Exceeded) This error indicates that the application has exhausted its maximum number of allotted API calls allowed for a given duration. If the client receives a Rate Limit Exceeded response the client should process this response and halt future API calls for the duration, in seconds, indicated by the Retry-After header. Applications that are in violation of this policy may have their access disabled to preserve the integrity of the API. Please refer to our Rate Limiting documentation below for more information on determining if you have been rate limited, and how to avoid it.
    </pre>
    <H1>Ref</H1>
    https://developer.riotgames.com/docs/portal
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class V1RiotAPIService extends AbstractRestService {
    private final RestTemplate restTemplate;
    private final RiotURLResolver riotURLResolver;

    @Override
    public String getPUUID(String gameName, String tagLine) {
        final String fullURL = riotURLResolver.getRiotAccountURL(gameName, tagLine);

        return doExecute(() -> {
            AccountDTO body = restTemplate.getForEntity(fullURL, AccountDTO.class).getBody();

            return Optional.ofNullable(body)
                .map(AccountDTO::getPuuid)
                .orElse(null);
        });
    }
    @Override
    public List<String> getMatchIdList(String puuid) {
        final String fullURL = riotURLResolver.getMatchListURL(puuid);

        return doExecute(() ->
            restTemplate.exchange(fullURL, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {}).getBody()
        );
    }
    @Override
    public Object getMatchDetails(String matchId) {
        final String fullURL = riotURLResolver.getMatchDetailURL(matchId);

        return doExecute(() ->
            restTemplate.getForObject(fullURL, Object.class)
        );
    }
}
