#version 430 core
layout(location = 0) in vec2 vertexPosition;

uniform mat4 VPtransform;
uniform mat4 invVPtransform;

void main()
{
	vec4 pt1 = vec4(vertexPosition, -1.0, 1.0);
	vec4 pt2 = vec4(vertexPosition, 1.0, 1.0);
	
	vec4 L = vec4( normalize(vec3(0, 1, 0)), 0);
	L = transpose(invVPtransform) * L;
	vec4 V = normalize(pt2 - pt1);

	float t =  - dot(L,pt1) / dot(L, V);
	vec4 projectedPt = pt1 + t*V;	

	projectedPt = invVPtransform * projectedPt;
	gl_Position = projectedPt;
}