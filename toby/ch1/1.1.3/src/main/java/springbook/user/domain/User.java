package springbook.user.domain;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
public class User {
	String id;
	String name;
	String password;
}
