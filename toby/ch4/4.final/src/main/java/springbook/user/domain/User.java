package springbook.user.domain;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
	String id;
	String name;
	String password;
}