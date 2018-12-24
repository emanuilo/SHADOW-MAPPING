#version 430
in vec3 interpolatedVertexColor;
in vec3 interpolatedNormal;
in vec3 lightPosInCameraSpace;
in vec3 vertexPosInCameraSpace;

uniform vec3 SpecularColor;
uniform float SpecularPow;
uniform vec2 Coefficients;

out vec4 outColor;
void main()
{
	vec3 viewVec = normalize(-vertexPosInCameraSpace);
	vec3 lightVec = normalize(lightPosInCameraSpace - vertexPosInCameraSpace);
	vec3 normal = normalize(interpolatedNormal);
	vec3 reflected = reflect(-lightVec, normal);
	float lambert = max(dot(normal, lightVec), 0);
	float specular = pow( max(dot(viewVec, reflected), 0), SpecularPow);
	outColor = Coefficients.x * vec4(lambert*interpolatedVertexColor, 1.0) + Coefficients.y * vec4(SpecularColor, 1.0)*specular;
}
