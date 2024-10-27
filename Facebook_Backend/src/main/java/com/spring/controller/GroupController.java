package com.spring.controller;

import com.spring.config.JwtUtil;
import com.spring.dto.Request.Group.*;
import com.spring.dto.Request.User.CommentRequest;
import com.spring.dto.Response.Group.*;
import com.spring.dto.Response.User.CommentResponse;
import com.spring.dto.Response.User.UserResponse;
import com.spring.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/group")
@RequiredArgsConstructor
public class GroupController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RoleService roleService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupMemberService groupMemberService;

    @Autowired
    private GroupMessageService groupMessageService;

    @Autowired
    private GroupPostService groupPostService;

    @Autowired
    private CommentService commentService;

    // TODO: Role
    @PostMapping("/role/add")
    public ResponseEntity<RoleResponse> addRole(@RequestBody RoleRequest roleRequest) {
        RoleResponse roleResponse = roleService.createRole(roleRequest);
        return ResponseEntity.ok(roleResponse);
    }

    @GetMapping("/role/getRoleById")
    public ResponseEntity<RoleResponse> getRoleById(@RequestBody RoleRequest roleRequest) {
        RoleResponse roleResponse = roleService.getRoleById(roleRequest.getRoleId());
        return ResponseEntity.ok(roleResponse);
    }

    @GetMapping("/role/getAllRoles")
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        List<RoleResponse> roleResponses = roleService.getAllRoles();
        return ResponseEntity.ok(roleResponses);
    }

    @PutMapping("/role/edit")
    public ResponseEntity<RoleResponse> editRole(@RequestBody RoleRequest roleRequest) {
        RoleResponse roleResponse = roleService.updateRole(roleRequest);
        return ResponseEntity.ok(roleResponse);
    }

    @DeleteMapping("/role/delete/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok("Delete Role Successfully");
    }

    // TODO: Group
    @PostMapping("/add")
    public ResponseEntity<GroupResponse> addGroup(@RequestBody GroupRequest groupRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        GroupResponse groupResponse = groupService.createGroup(userId, groupRequest);
        return ResponseEntity.ok(groupResponse);
    }

    @GetMapping("/getGroupById")
    public ResponseEntity<GroupResponse> getRoleById(@RequestBody GroupRequest groupRequest) {
        GroupResponse groupResponse = groupService.getGroupById(groupRequest);
        return ResponseEntity.ok(groupResponse);
    }

    @GetMapping("/getAllGroups")
    public ResponseEntity<List<GroupResponse>> getAllGroups() {
        List<GroupResponse> groupResponses = groupService.getAllGroups();
        return ResponseEntity.ok(groupResponses);
    }

    @PutMapping("/edit")
    public ResponseEntity<GroupResponse> editGroup(@RequestBody GroupRequest groupRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        GroupResponse groupResponse = groupService.updateGroup(userId, groupRequest);
        return ResponseEntity.ok(groupResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteGroup(@PathVariable Integer id) {
        groupService.deleteGroup(id);
        return ResponseEntity.ok("Delete Group Successfully");
    }

    // TODO: GroupMember
    @PostMapping("/member/add")
    public ResponseEntity<GroupMemberResponse> addMember(@RequestBody GroupMemberRequest groupMemberRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        GroupMemberResponse groupMemberResponse = groupMemberService.addMemberToGroup(userId, groupMemberRequest);
        return ResponseEntity.ok(groupMemberResponse);
    }

    @GetMapping("/member/getMembersByGroupId")
    public ResponseEntity<List<GroupMemberResponse>> getMembersByGroupId(@RequestBody GroupMemberRequest groupMemberRequest) {
        List<GroupMemberResponse> groupMembers = groupMemberService.getMembersByGroupId(groupMemberRequest);
        return ResponseEntity.ok(groupMembers);
    }

    @DeleteMapping("/member/remove")
    public ResponseEntity<String> removeMember(@RequestBody GroupMemberRequest groupMemberRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        groupMemberService.removeMemberFromGroup(userId, groupMemberRequest);
        return ResponseEntity.ok("Member removed successfully");
    }

    // TODO: GroupMessage
    @PostMapping("/message/send")
    public ResponseEntity<GroupMessageResponse> sendMessage(@RequestBody GroupMessageRequest groupMessageRequest, HttpServletRequest request) {
        Integer sourceId = jwtUtil.getUserIdFromToken(request);
        GroupMessageResponse groupMessageResponse = groupMessageService.sendMessage(sourceId, groupMessageRequest);
        return ResponseEntity.ok(groupMessageResponse);
    }

    @PutMapping("/message/edit/{messageId}")
    public ResponseEntity<GroupMessageResponse> editMessage(
            @PathVariable Integer messageId,
            @RequestBody GroupMessageRequest groupMessageRequest,
            HttpServletRequest request) {
        Integer sourceId = jwtUtil.getUserIdFromToken(request);
        GroupMessageResponse groupMessageResponse = groupMessageService.editMessage(sourceId, messageId, groupMessageRequest);
        return ResponseEntity.ok(groupMessageResponse);
    }

    @DeleteMapping("/message/delete/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable Integer messageId, HttpServletRequest request) {
        Integer sourceId = jwtUtil.getUserIdFromToken(request);
        groupMessageService.deleteMessage(sourceId, messageId);
        return ResponseEntity.ok("Message deleted successfully");
    }

    @GetMapping("/message/findBySourceId")
    public ResponseEntity<List<GroupMessageResponse>> findMessagesBySourceId(HttpServletRequest request) {
        Integer sourceId = jwtUtil.getUserIdFromToken(request);
        List<GroupMessageResponse> messages = groupMessageService.findBySourceId(sourceId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/message/findByTargetId")
    public ResponseEntity<List<GroupMessageResponse>> findMessagesByTargetId(@RequestBody GroupMessageRequest groupMessageRequest) {
        List<GroupMessageResponse> messages = groupMessageService.findByTargetId(groupMessageRequest);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/message/getMessagesByGroupId")
    public ResponseEntity<List<GroupMessageResponse>> getMessagesByGroupId(@RequestBody GroupMessageRequest groupMessageRequest) {
        List<GroupMessageResponse> messages = groupMessageService.getMessagesByGroupId(groupMessageRequest);
        return ResponseEntity.ok(messages);
    }

    // TODO: GroupPost
    @PostMapping("/post/add")
    public ResponseEntity<GroupPostResponse> addPost(@RequestBody GroupPostRequest groupPostRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        GroupPostResponse groupPostResponse = groupPostService.addPost(userId, groupPostRequest);
        return ResponseEntity.ok(groupPostResponse);
    }

    @PutMapping("/post/edit")
    public ResponseEntity<GroupPostResponse> editPost(@RequestBody GroupPostRequest groupPostRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        GroupPostResponse groupPostResponse = groupPostService.editPost(userId, groupPostRequest);
        return ResponseEntity.ok(groupPostResponse);
    }

    @DeleteMapping("/post/delete")
    public ResponseEntity<String> deletePost(@RequestBody GroupPostRequest groupPostRequest) {
        groupPostService.deletePost(groupPostRequest);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @GetMapping("/post/getById")
    public ResponseEntity<GroupPostResponse> getPostById(@RequestBody GroupPostRequest groupPostRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        GroupPostResponse post = groupPostService.getPostById(userId, groupPostRequest);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/post/getByGroupId")
    public ResponseEntity<List<GroupPostResponse>> getPostsByGroupId(@RequestBody GroupPostRequest groupPostRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<GroupPostResponse> posts = groupPostService.getPostsByGroupId(userId, groupPostRequest);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/post/getByUserId")
    public ResponseEntity<List<GroupPostResponse>> getPostsByUserId(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<GroupPostResponse> posts = groupPostService.getUserPosts(userId);
        return ResponseEntity.ok(posts);
    }

    //  TODO: Comment
    @PostMapping("/comment/add")
    public ResponseEntity<CommentResponse> addComment(@RequestBody CommentRequest commentRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        CommentResponse response = commentService.addGroupComment(userId, commentRequest);
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
    public ResponseEntity<List<CommentResponse>> getCommentsByGroupPostId(@RequestBody CommentRequest commentRequest) {
        List<CommentResponse> comments = commentService.getCommentsByGroupPostId(commentRequest);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comments/getCommentByUser")
    public ResponseEntity<List<CommentResponse>> getCommentsByUserId(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<CommentResponse> comments = commentService.getCommentsByUserId(userId);
        return ResponseEntity.ok(comments);
    }

    // TODO: UserLikePost
    @PostMapping("/post/like")
    public ResponseEntity<String> likePost(@RequestBody GroupPostRequest postRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        Boolean liked = groupPostService.likePost(userId, postRequest);
        return ResponseEntity.ok(liked ? "Post liked" : "Post unliked");
    }

    @GetMapping("/post/viewUsersLike")
    public ResponseEntity<List<UserResponse>> getUsersWhoLikedPost(@RequestBody GroupPostRequest postRequest) {
        List<UserResponse> usersWhoLiked = groupPostService.getUsersWhoLikedPost(postRequest);
        return ResponseEntity.ok(usersWhoLiked);
    }
}
