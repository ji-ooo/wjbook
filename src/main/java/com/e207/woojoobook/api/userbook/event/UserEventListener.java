package com.e207.woojoobook.api.userbook.event;

import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.e207.woojoobook.domain.chatroom.ChatRoom;
import com.e207.woojoobook.domain.chatroom.ChatRoomRepository;
import com.e207.woojoobook.domain.rental.Rental;
import com.e207.woojoobook.domain.rental.RentalRepository;
import com.e207.woojoobook.domain.user.User;
import com.e207.woojoobook.domain.user.UserRepository;
import com.e207.woojoobook.domain.userbook.Userbook;
import com.e207.woojoobook.domain.userbook.UserbookRepository;
import com.e207.woojoobook.domain.userbook.Wishbook;
import com.e207.woojoobook.domain.userbook.WishbookRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserEventListener {

	private final UserRepository userRepository;
	private final UserbookRepository userbookRepository;
	private final WishbookRepository wishBookRepository;
	private final RentalRepository rentalRepository;
	private final ChatRoomRepository chatRoomRepository;

	@Transactional
	@EventListener
	public void handleUserDeleteEvent(UserDeleteEvent event) {
		User user = event.user();
		List<Userbook> userbooks = this.userbookRepository.findWithUserByUser(user);
		List<Wishbook> wishbooks = this.wishBookRepository.findWithUserByUser(user);
		List<Rental> rentals = this.rentalRepository.findWithUserByUser(user);
		List<ChatRoom> allBySender = this.chatRoomRepository.findAllBySender(user);
		List<ChatRoom> allByReceiver = this.chatRoomRepository.findAllByReceiver(user);

		userbooks.forEach(Userbook::removeUser);
		wishbooks.forEach(Wishbook::removeUser);
		rentals.forEach(Rental::removeUser);
		allBySender.forEach(ChatRoom::removeSender);
		allByReceiver.forEach(ChatRoom::removeReceiver);
		this.userRepository.delete(user);
	}
}
