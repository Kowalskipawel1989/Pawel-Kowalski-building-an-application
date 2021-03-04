package com.crud.tasks.trello.client;


import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import static java.util.Optional.ofNullable;

@Component
public class TrelloClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);

@Value("${trello.api.endpoint.prod}")
private String trelloApiEndpoint;

@Value("${trello.app.key}")
private String trelloAppKey;

@Value("${trello.app.token}")
private String trelloToken;

@Value("${trello.service.username}")
private String trelloUserName;

@Autowired
private RestTemplate restTemplate;

    public List<TrelloBoardDto> getTrelloBoards() {

        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/pawelkowalski53/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id")
                .queryParam("lists", "all").build().encode().toUri();

        try {
            TrelloBoardDto[] boardsResponse = restTemplate.getForObject(url, TrelloBoardDto[].class);
            return Arrays.asList(ofNullable(boardsResponse).orElse(new TrelloBoardDto[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }


    }
    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto){
        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/cards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos",trelloCardDto.getPos())
                .queryParam("idList",trelloCardDto.getListId()).build().encode().toUri();
        return restTemplate.postForObject(url,null,CreatedTrelloCard.class);
    }

}
