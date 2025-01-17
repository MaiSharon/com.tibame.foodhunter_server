package ai_ying.service;

import java.util.List;
import java.util.Set;

import com.google.gson.JsonElement;

import ai_ying.vo.FcmToken;
import ai_ying.vo.Group;
import ai_ying.vo.GroupChat;
import ai_ying.vo.GroupMember;
import andysearch.vo.Restaurant;
import jessey.vo.Review;
import member.vo.Member;

public interface GroupService {
	// 建立揪團
	String createGroup(Group group);

	// 搜尋揪團
	List<Group> searchGroups(Group group);

	// 取得參加揪團清單
	List<Group> getGroupList(Member member);

	// 參加揪團
	String joinGroup(GroupMember groupMember);

	// 取得gorupId
	int getGroupId(Group group);

	// 傳送訊息
	String sendMessage(GroupChat groupChat);

	// 取得聊天紀錄
	List<GroupChat> getGroupChatHistory(Group group);
	
	// 註冊FCM token
	String registerFcm(FcmToken fcmToken);
	
	// 取得特定group的token清單
	Set<String> getTokens(Integer groupId);
	
	// 離開群組
	String leaveGroup(GroupMember groupMember);
	
	// 取得頭像清單
	List<Member> getAvatars(Group group);
	
	// 取得餐廳清單
	List<Restaurant> getRestaurantList();

	// 取得餐廳評論
	Review getRestaurantReview(Review review);

	// 新增或更新評論
	String sendRestaurantReview(Review review);
}
