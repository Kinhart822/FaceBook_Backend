package com.spring.controller;

import com.spring.config.JwtUtil;
import com.spring.dto.Request.User.*;
import com.spring.dto.Response.User.*;
import com.spring.entities.User;
import com.spring.enums.FriendRequestStatus;
import com.spring.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserAboutService userAboutService;

    @Autowired
    private UserFollowerService userFollowerService;

    @Autowired
    private UserMessageService userMessageService;

    @Autowired
    private UserFriendService userFriendService;

    @Autowired
    private UserPostService userPostService;

    @Autowired
    private VideoPostService videoPostService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseEntity<String> sayHello(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        return ResponseEntity.ok("Hello, User! Your ID is: " + userId);
    }

    // TODO: Search User
    @GetMapping("/search-user-by-firstname")
    public ResponseEntity<List<SearchUserByNameResponse>> getUsersByFirstName(
            @RequestParam(required = false, name = "firstName") String firstName,
            @RequestParam(required = false, name = "limit", defaultValue = "10") Integer limit,
            @RequestParam(required = false, name = "offset", defaultValue = "0") Integer offset,
            HttpServletRequest request
    ) {
        Integer currentUserId = jwtUtil.getUserIdFromToken(request);
        List<SearchUserByNameResponse> users = userService.getUsersByFirstName(firstName, currentUserId, limit, offset);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search-user-by-lastname")
    public ResponseEntity<List<SearchUserByNameResponse>> getUsersByLastName(
            @RequestParam(required = false, name = "lastName") String lastName,
            @RequestParam(required = false, name = "limit", defaultValue = "10") Integer limit,
            @RequestParam(required = false, name = "offset", defaultValue = "0") Integer offset,
            HttpServletRequest request
    ) {
        Integer currentUserId = jwtUtil.getUserIdFromToken(request);
        List<SearchUserByNameResponse> users = userService.getUsersByLastName(lastName, currentUserId, limit, offset);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search-user-by-username")
    public ResponseEntity<List<SearchUserByUserNameResponse>> getUsersByUserName(
            @RequestParam(required = false, name = "userName") String userName,
            @RequestParam(required = false, name = "limit", defaultValue = "10") Integer limit,
            @RequestParam(required = false, name = "offset", defaultValue = "0") Integer offset,
            HttpServletRequest request
    ) {
        Integer currentUserId = jwtUtil.getUserIdFromToken(request);
        List<SearchUserByUserNameResponse> users = userService.getUsersByUserName(userName, currentUserId, limit, offset);
        return ResponseEntity.ok(users);
    }



    // TODO: UserAbout and Location
    @GetMapping("/listUserAbout")
    public ResponseEntity<List<UserAboutResponse>> getAllUserAbout() {
        List<UserAboutResponse> userAboutList = userAboutService.findAll();
        return ResponseEntity.ok(userAboutList);
    }

    @GetMapping("/userAbout/{id}")
    public ResponseEntity<UserAboutResponse> getUserAboutById(@PathVariable Integer id) {
        UserAboutResponse userAbout = userAboutService.findById(id);
        return ResponseEntity.ok(userAbout);
    }

    @PostMapping("/createUserAbout")
    public ResponseEntity<UserAboutResponse> createUserAbout(@RequestBody UserAboutRequest userAboutRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        UserAboutResponse userAboutResponse = userAboutService.save(userAboutRequest, userId);
        return new ResponseEntity<>(userAboutResponse, HttpStatus.CREATED);
    }
    
    // TODO: UserFollower
    @PostMapping("/follow")
    public ResponseEntity<String> followUser(@RequestBody UserFollowerRequest userFollowerRequest, HttpServletRequest request) {
        Integer sourceUserId = jwtUtil.getUserIdFromToken(request);
        userFollowerService.followUser(sourceUserId, userFollowerRequest);
        return ResponseEntity.ok("Successfully followed user with ID: " + userFollowerRequest.getTargetUserId());
    }

    @DeleteMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(@RequestBody UserFollowerRequest userFollowerRequest, HttpServletRequest request) {
        Integer sourceUserId = jwtUtil.getUserIdFromToken(request);
        userFollowerService.unfollowUser(sourceUserId, userFollowerRequest);
        return ResponseEntity.ok("Successfully unfollowed user with ID: " + userFollowerRequest.getTargetUserId());
    }

    @GetMapping("/getFollowing")
    public ResponseEntity<List<UserFollowerResponse>> getFollowingList(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<User> followingList = userFollowerService.getFollowingList(userId);
        List<UserFollowerResponse> followingResponses = followingList.stream()
                .map(user -> UserFollowerResponse.builder()
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .dateOfBirth(user.getDateOfBirth())
                        .gender(user.getGender().toString())
                        .build())
                .toList();
        return ResponseEntity.ok(followingResponses);
    }

    @GetMapping("/getFollowers")
    public ResponseEntity<List<UserFollowerResponse>> getFollowersList(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<User> followersList = userFollowerService.getFollowersList(userId);
        List<UserFollowerResponse> followersResponses = followersList.stream()
                .map(user -> UserFollowerResponse.builder()
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .dateOfBirth(user.getDateOfBirth())
                        .gender(user.getGender().toString())
                        .build())
                .toList();
        return ResponseEntity.ok(followersResponses);
    }

    // TODO: UserMessage
    @PostMapping("/sendMessage")
    public ResponseEntity<UserMessageResponse> sendMessage(
            @RequestBody UserMessageRequest userMessageRequest,
            HttpServletRequest request) {
        Integer sourceId = jwtUtil.getUserIdFromToken(request);
        UserMessageResponse response = userMessageService.sendMessage(sourceId, userMessageRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/editMessage/{messageId}")
    public ResponseEntity<UserMessageResponse> editMessage(
            @PathVariable Integer messageId,
            @RequestBody UserMessageRequest userMessageRequest,
            HttpServletRequest request) {
        Integer sourceId = jwtUtil.getUserIdFromToken(request);
        UserMessageResponse response = userMessageService.editMessage(sourceId, messageId, userMessageRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteMessage/{messageId}")
    public ResponseEntity<String> deleteMessage(
            @PathVariable Integer messageId,
            HttpServletRequest request) {
        Integer sourceId = jwtUtil.getUserIdFromToken(request);
        userMessageService.deleteMessage(sourceId, messageId);
        return ResponseEntity.ok("Message deleted successfully");
    }

    @GetMapping("/messages/source")
    public ResponseEntity<List<UserMessageResponse>> getMessagesBySourceId(HttpServletRequest request) {
        Integer sourceId = jwtUtil.getUserIdFromToken(request);
        List<UserMessageResponse> messages = userMessageService.findBySourceId(sourceId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/messages/target")
    public ResponseEntity<List<UserMessageResponse>> getMessagesByTargetId(@RequestBody UserMessageRequest userMessageRequest) {
        List<UserMessageResponse> messages = userMessageService.findByTargetId(userMessageRequest);
        return ResponseEntity.ok(messages);
    }

    // TODO: UserFriend
    @PostMapping("/friend/request")
    public ResponseEntity<String> sendFriendRequest(@RequestBody UserFriendRequest userFriendRequest, HttpServletRequest request) {
        Integer sourceId = jwtUtil.getUserIdFromToken(request);
        userFriendService.sendFriendRequest(sourceId, userFriendRequest);
        return ResponseEntity.ok("Friend request sent to user ID: " + userFriendRequest.getTargetUserId());
    }

    @PutMapping("/friend/accept")
    public ResponseEntity<String> acceptFriendRequest(@RequestParam Integer sourceId, HttpServletRequest request) {
        Integer targetId = jwtUtil.getUserIdFromToken(request);
        userFriendService.acceptFriendRequest(sourceId, targetId);
        return ResponseEntity.ok("Friend request accepted from user ID: " + sourceId);
    }

    @PutMapping("/friend/decline")
    public ResponseEntity<String> declineFriendRequest(@RequestParam Integer sourceId, HttpServletRequest request) {
        Integer targetId = jwtUtil.getUserIdFromToken(request);
        userFriendService.declineFriendRequest(sourceId, targetId);
        return ResponseEntity.ok("Friend request declined from user ID: " + sourceId);
    }

    @DeleteMapping("/friend/remove-friend")
    public ResponseEntity<String> removeFriend(@RequestBody UserFriendRequest userFriendRequest, HttpServletRequest request) {
        Integer sourceId = jwtUtil.getUserIdFromToken(request);
        userFriendService.removeFriend(sourceId, userFriendRequest);
        return ResponseEntity.ok("Friend removed successfully");
    }

    @GetMapping("/friend/requests/pending")
    public ResponseEntity<List<UserFriendResponse>> getPendingFriendRequests(HttpServletRequest request) {
        Integer targetId = jwtUtil.getUserIdFromToken(request);
        List<UserFriendResponse> pendingRequests = userFriendService.getPendingFriendRequests(targetId, FriendRequestStatus.PENDING);
        return ResponseEntity.ok(pendingRequests);
    }

    @GetMapping("/friend/requests/accepted")
    public ResponseEntity<List<UserFriendResponse>> getAcceptedFriendRequests(HttpServletRequest request) {
        Integer targetId = jwtUtil.getUserIdFromToken(request);
        List<UserFriendResponse> acceptedRequests = userFriendService.getAcceptedFriendRequests(targetId, FriendRequestStatus.ACCEPTED);
        return ResponseEntity.ok(acceptedRequests);
    }

    // TODO: UserPost
    @PostMapping("/post/add")
    public ResponseEntity<PostResponse> addPost(@RequestBody PostRequest postRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        PostResponse response = userPostService.addPost(userId, postRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/post/edit")
    public ResponseEntity<PostResponse> editPost(@RequestBody PostRequest postRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        PostResponse response = userPostService.editPost(userId, postRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/post/delete")
    public ResponseEntity<String> deletePost(@RequestBody PostRequest postRequest) {
        userPostService.deletePost(postRequest);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @GetMapping("/postById")
    public ResponseEntity<PostResponse> getPostById(@RequestBody PostRequest postRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        PostResponse response = userPostService.getPostById(userId, postRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/postsByUser")
    public ResponseEntity<List<PostResponse>> getUserPosts(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<PostResponse> posts = userPostService.getUserPosts(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/imagesByUserPostAndPostStatus")
    public ResponseEntity<List<String>> getImageUrls(@RequestBody PostRequest postRequest) {
        List<String> posts = userPostService.getAllImageUrlByUserPostAndPostStatus(postRequest);
        return ResponseEntity.ok(posts);
    }

    // TODO: VideoPost
    @PostMapping("/videoPost/add")
    public ResponseEntity<VideoPostResponse> addPost(@RequestBody VideoPostRequest videoPostRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        VideoPostResponse response = videoPostService.addPost(userId, videoPostRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/videoPost/edit")
    public ResponseEntity<VideoPostResponse> editPost(@RequestBody VideoPostRequest videoPostRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        VideoPostResponse response = videoPostService.editPost(userId, videoPostRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/videoPost/delete")
    public ResponseEntity<String> deletePost(@RequestBody VideoPostRequest videoPostRequest) {
        videoPostService.deletePost(videoPostRequest);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @GetMapping("/videoPostById")
    public ResponseEntity<VideoPostResponse> getPostById(@RequestBody VideoPostRequest videoPostRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        VideoPostResponse response = videoPostService.getPostById(userId, videoPostRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/videoPostsByUser")
    public ResponseEntity<List<VideoPostResponse>> getVideoPosts(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<VideoPostResponse> posts = videoPostService.getVideoPosts(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/videoByVideoPostAndPostStatus")
    public ResponseEntity<String> getImageUrls(@RequestBody VideoPostRequest videoPostRequest) {String post = videoPostService.getVideoUrlByVideoPostAndPostStatus(videoPostRequest);
        return ResponseEntity.ok(post);
    }

    //  TODO: Comment
    @PostMapping("/comment/add")
    public ResponseEntity<CommentResponse> addComment(@RequestBody CommentRequest commentRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        CommentResponse response = commentService.addComment(userId, commentRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/comment/edit")
    public ResponseEntity<CommentResponse> editComment(@RequestBody CommentRequest commentRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        CommentResponse response = commentService.editComment(userId, commentRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/comment/delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }

    @GetMapping("/comments/getCommentByPost")
    public ResponseEntity<List<CommentResponse>> getCommentsByUserPostId(@RequestBody CommentRequest commentRequest) {
        List<CommentResponse> comments = commentService.getCommentsByUserPostId(commentRequest);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comments/getCommentByUser")
    public ResponseEntity<List<CommentResponse>> getCommentsByUserId(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<CommentResponse> comments = commentService.getCommentsByUserId(userId);
        return ResponseEntity.ok(comments);
    }

    //  TODO: VideoComment
    @PostMapping("/videoComment/add")
    public ResponseEntity<CommentResponse> addVideoComment(@RequestBody CommentRequest commentRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        CommentResponse response = commentService.addVideoComment(userId, commentRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/videoComment/edit")
    public ResponseEntity<CommentResponse> editVideoComment(@RequestBody CommentRequest commentRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        CommentResponse response = commentService.editComment(userId, commentRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/videoComment/delete/{commentId}")
    public ResponseEntity<String> deleteVideoComment(@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }

    @GetMapping("/videoComments/getCommentByPost")
    public ResponseEntity<List<CommentResponse>> getCommentsByVideoPostId(@RequestBody CommentRequest commentRequest) {
        List<CommentResponse> comments = commentService.getCommentsByVideoPostId(commentRequest);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/videoComments/getCommentByUser")
    public ResponseEntity<List<CommentResponse>> getVideoCommentsByUserId(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<CommentResponse> comments = commentService.getCommentsByUserId(userId);
        return ResponseEntity.ok(comments);
    }

    // TODO: UserLikePost
    @PostMapping("/post/like")
    public ResponseEntity<String> likePost(@RequestBody PostRequest postRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        Boolean liked = userPostService.likePost(userId, postRequest);
        return ResponseEntity.ok(liked ? "Post liked" : "Post unliked");
    }

    @GetMapping("/post/viewUsersLike")
    public ResponseEntity<List<UserResponse>> getUsersWhoLikedPost(@RequestBody PostRequest postRequest) {
        List<UserResponse> usersWhoLiked = userPostService.getUsersWhoLikedPost(postRequest);
        return ResponseEntity.ok(usersWhoLiked);
    }

    @PostMapping("/videoPost/like")
    public ResponseEntity<String> likePost(@RequestBody VideoPostRequest videoPostRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        Boolean liked = videoPostService.likePost(userId, videoPostRequest);
        return ResponseEntity.ok(liked ? "Post liked" : "Post unliked");
    }

    @GetMapping("/videoPost/viewUsersLike")
    public ResponseEntity<List<UserResponse>> getUsersWhoLikedPost(@RequestBody VideoPostRequest videoPostRequest) {
        List<UserResponse> usersWhoLiked = videoPostService.getUsersWhoLikedPost(videoPostRequest);
        return ResponseEntity.ok(usersWhoLiked);
    }

    // TODO: Notifications
    @GetMapping("/notifications/getNotificationsByUser")
    public ResponseEntity<NotificationResponse> getNotificationsByUser(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        NotificationResponse notifications = notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/notifications/markAsRead")
    public ResponseEntity<String> markNotificationAsRead(@RequestBody NotificationRequest notificationRequest) {
        notificationService.markNotificationAsRead(notificationRequest);
        return ResponseEntity.ok("Notification marked as read");
    }

}

