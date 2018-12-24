#version 430 core
layout(location = 0) in vec3 vertexPosition;
layout(location = 1) in vec3 vertexColor;
layout(location = 2) in vec3 vertexNormal;
out vec4 interpolatedVertexColor;
uniform mat4 MVPTransform;
uniform mat4 MVTransform;
uniform mat3 NormalTransform;
uniform vec3 LightPosition;

void main()
{
	vec3 normalInCameraSpace = normalize(NormalTransform * vertexNormal);
	vec4 lightPosInCameraSpace = MVTransform * vec4(LightPosition, 1); // can be computed on CPU side
	vec4 vertexPosInCameraSpace = MVTransform * vec4(vertexPosition, 1.0);
	vec3 lightVec = normalize(vec3(lightPosInCameraSpace.xyz - vertexPosInCameraSpace.xyz));
	float lambert = max(dot(normalInCameraSpace, lightVec), 0);
	interpolatedVertexColor = vec4(lambert * vertexColor, 1);
	gl_Position = MVPTransform * vec4(vertexPosition, 1.0);
}
